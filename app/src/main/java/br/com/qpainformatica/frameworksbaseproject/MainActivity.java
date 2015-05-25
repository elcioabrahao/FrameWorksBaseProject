package br.com.qpainformatica.frameworksbaseproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import net.bozho.easycamera.DefaultEasyCamera;
import net.bozho.easycamera.EasyCamera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.qpainformatica.frameworksbaseproject.entities.Contato;
import br.com.qpainformatica.frameworksbaseproject.utils.CLog;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hugo.weaving.DebugLog;


public class MainActivity extends ActionBarActivity {


    @InjectView(R.id.editTextNome)
    EditText editTextNome;
    @InjectView(R.id.editTextTelefone)
    EditText editTextTelefone;
    @InjectView(R.id.checkBoxLigar)
    CheckBox checkBoxLigar;
    @InjectView(R.id.buttonEnviar)
    Button buttonEnviar;
    @InjectView(R.id.imageButton)
    ImageButton imageButton;
    @InjectView(R.id.surfaceView)
    SurfaceView surface;
    @InjectView(R.id.imageViewFoto)
    ImageView imageViewFoto;

    // para o preview da foto
    SurfaceHolder previewHolder;
    private boolean previewIsRunning;
    private EasyCamera camera;
    // -------------------------------

    // nome da imagem gerada
    private String nomeImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // metodo abaixo necessário para injections
        ButterKnife.inject(this);

        // para iniciar o nome da Imagem
        nomeImagem = "";

        // para iniciar o preview de camera
        initCameraPreview();

    }

    @OnClick(R.id.buttonEnviar)
    void enviar() {

        salvarNoBanco();

        // Teste do Hugo
        String retorno = testeLog("Elcio", "Abrahão");
    }

    private void initCameraPreview() {

        previewHolder = surface.getHolder();
        previewHolder.addCallback(new SurfaceHolder.Callback() {

            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                if (!previewIsRunning && (camera != null)) {
                    try {
                        camera.startPreview(holder);
                    } catch (IOException e) {
                        CLog.e("amera", "erro: " + e.getMessage());
                        e.printStackTrace();
                    }
                    previewIsRunning = true;
                }
            }

            public void surfaceCreated(SurfaceHolder holder) {

                camera = DefaultEasyCamera.open();

            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                camera.close();
                camera = null;

            }

        });
    }

    @OnClick(R.id.imageButton)
    void tirarFoto() {


        EasyCamera.CameraActions actions = null;
        try {
            actions = camera.startPreview(previewHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        EasyCamera.PictureCallback callback = new EasyCamera.PictureCallback() {
            public void onPictureTaken(byte[] data, EasyCamera.CameraActions actions) {
                CLog.i("Foto", "Foto tirada");
                nomeImagem = salvarFoto(data);
                try {
                    camera.startPreview(previewHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        actions.takePicture(EasyCamera.Callbacks.create().withJpegCallback(callback));

    }

    private String salvarFoto(byte[] data){

        Bitmap thumbnail  = BitmapFactory.decodeByteArray(data, 0, data.length);
        // view onde está a foto no meu layout
        imageViewFoto.setImageBitmap(thumbnail);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd_hhmmss");
        String timeStamp = s.format(new Date());
        String nomeImagem = Environment.getExternalStorageDirectory()+File.separator + "image_"+timeStamp+".jpg";

        File file = new File(nomeImagem);
        try {
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);

            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            CLog.e("Foto","Erro: "+e.getMessage());
            e.printStackTrace();
        }
        return nomeImagem;
    }

    // anotação para fazer LOG
    @DebugLog
    public String testeLog(String primeiroNome, String ultimoNome) {

        return primeiroNome + " " + ultimoNome;
    }


    public void salvarNoBanco() {

        Contato contato = null;

        // Se não estão vazios grava no banco de dados
        if (!editTextNome.equals("") && !editTextTelefone.equals("")) {
            contato = new Contato(editTextNome.getText().toString(), editTextTelefone.getText().toString(),nomeImagem);
            contato.save();
            Toast.makeText(this, "Salvando...", Toast.LENGTH_SHORT).show();
        }

        /**
         // Outos métodos do Sugar

         contato = Contato.findById(Contato.class, 1L);

         contato = Contato.findById(Contato.class, 1L);
         contato.nome = "updated title here"; // modify the values
         contato.telefone = "3rd edition";
         contato.save(); // updates the previous entry with new values.

         contato = Contato.findById(Contato.class, 1L);
         contato.delete();

         Contato.deleteAll(Contato.class);
         */


        List<Contato> contatos = Contato.listAll(Contato.class);

        for (Contato cont : contatos) {
            CLog.i("Contatos", "Nome: " + cont.getNome() + ", tel:" + cont.getTelefone()+ ", Imagem:" + cont.getNomeImagem());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
