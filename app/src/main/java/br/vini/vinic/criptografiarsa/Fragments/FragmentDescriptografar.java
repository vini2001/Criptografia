package br.vini.vinic.criptografiarsa.Fragments;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.vini.vinic.criptografiarsa.AritmeticaModular;
import br.vini.vinic.criptografiarsa.MainActivity;
import br.vini.vinic.criptografiarsa.R;

public class FragmentDescriptografar extends Fragment {

    private MainActivity tela;
    private Button btn, colar, limpar;
    private EditText txt;

    public FragmentDescriptografar() {

    }

    public void setBundle(Bundle bundle){

    }

    public Bundle getBundle(){
        Bundle bundle = new Bundle();

        return bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_descriptografar, container, false);

        btn = (Button) view.findViewById(R.id.btn_Descriptografar);
        txt = (EditText) view.findViewById(R.id.edt_textocrip);
        colar = (Button) view.findViewById(R.id.btn_colar);
        limpar = (Button) view.findViewById(R.id.btn_limpar);

        colar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                txt.setText(clipboard.getPrimaryClip().getItemAt(0).getText().toString());
            }
        });

        limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText("");
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    String mensagem = descriptografar(txt.getText().toString());
                    String titulo = "Mensagem Descriptografada";

                    AlertDialog alerta;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("\n" + mensagem);
                    builder.setIcon(R.mipmap.ic_encrypt);
                    builder.setTitle(titulo);
                    alerta = builder.create();
                    alerta.show();

                }catch(Exception ex){
                    String titulo = "Erro";
                    AlertDialog alerta;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Algum erro ocorreu. Você colocou um texto criptografado com as chaves em uso?");
                    builder.setIcon(R.mipmap.ic_error);
                    builder.setTitle(titulo);
                    alerta = builder.create();
                    alerta.show();
                }
            }
        });


        return view;
    }

    private String descriptografar(String txt){

        String texto = txt;
        String numeros = "";
        String frase = "";

        tela = (MainActivity)getContext();

        if (texto.length() > 0)
        {
            String nums[] = texto.split("-");
            int blocos[] = new int[nums.length];

            for(int i = 0; i < nums.length; i++) blocos[i] = Integer.parseInt(nums[i]);

            for (int i = 0; i < nums.length; i++)
            {
                blocos[i] = AritmeticaModular.calcularResto((long)blocos[i], tela.getD(), tela.getN());
                numeros += String.valueOf(blocos[i]);
            }

            while(numeros.length() > 0){
                frase += AritmeticaModular.substituir2(Integer.parseInt(numeros.substring(0,3)));
                numeros = numeros.substring(3, numeros.length());
            }



        }
        return frase;
    }

}
