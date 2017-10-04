package br.vini.vinic.criptografiarsa.Fragments;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import br.vini.vinic.criptografiarsa.AritmeticaModular;
import br.vini.vinic.criptografiarsa.MainActivity;
import br.vini.vinic.criptografiarsa.R;

public class FragmentCriptografar extends Fragment {

    private MainActivity tela;
    private Button btn, btn_limpar;
    private EditText txt;

    public FragmentCriptografar() {

    }

    public void setBundle(Bundle bundle){

    }

    public Bundle getBundle(){
        Bundle bundle = new Bundle();

        return bundle;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_criptografar, container, false);

        txt = (EditText) view.findViewById(R.id.edt_texto);
        btn = (Button)   view.findViewById(R.id.btn_Criptografar);
        btn_limpar = (Button) view.findViewById(R.id.btn_limpar);


        btn_limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText("");
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tela = (MainActivity)getContext();

                if(!(tela.getE() > 0 && tela.getN() > 0)){
                    String titulo = "Erro";
                    AlertDialog alerta;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Algum erro ocorreu. Você já gerou as chaves??");
                    builder.setIcon(R.mipmap.ic_error);
                    builder.setTitle(titulo);
                    alerta = builder.create();
                    alerta.show();
                }else {
                    try {
                        final String mensagem = criptografar(txt.getText().toString());
                        String titulo = "Mensagem Criptografada";

                        AlertDialog alerta;
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("\n" + mensagem);
                        builder.setIcon(R.mipmap.ic_encrypt);
                        builder.setTitle(titulo);
                        builder.setPositiveButton("Copiar Texto", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("mensagem", mensagem);
                                clipboard.setPrimaryClip(clip);
                            }
                        });
                        alerta = builder.create();
                        alerta.show();
                    } catch (Exception ex) {
                        Log.e("Erro na criptografia",ex.getMessage());
                        String titulo = "Erro";
                        AlertDialog alerta;
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Algum erro ocorreu. Você já gerou as chaves??");
                        builder.setIcon(R.mipmap.ic_error);
                        builder.setTitle(titulo);
                        alerta = builder.create();
                        alerta.show();
                    }
                }

            }
        });

        return view;
    }

    private String criptografar(String txt){

        String texto = txt;
        String frase = "";

        tela = (MainActivity)getContext();

        if (texto.length() > 0)
        {
            char letras[] = texto.toCharArray();
            long numeros[] = new long[letras.length*3];

            int jafoi = 0;

            for (int i = 0; i < letras.length; i++)
            {
                int num = AritmeticaModular.substituir(letras[i]);
                Log.e("Numero", String.valueOf(num));
                numeros[jafoi] = num/100; jafoi++;
                numeros[jafoi] = (num/10)%10; jafoi++;
                numeros[jafoi] = (num%100)%10; jafoi ++;
            }



            long blocos[] = new long[((letras.length*3)/2)+1];

            jafoi = 0;
            int jafoiBlocos = 0;

            while (jafoi < numeros.length){
                if(numeros[jafoi] == 0){
                    blocos[jafoiBlocos-1] *= 10;
                    jafoi++;
                }else{
                    if(jafoi == numeros.length-1){
                        blocos[jafoiBlocos] = numeros[jafoi];
                        jafoi++;
                    }else {
                        blocos[jafoiBlocos] = Integer.parseInt(numeros[jafoi] + "" + numeros[jafoi + 1]);
                        jafoi += 2;
                    }
                    jafoiBlocos ++;
                }


            }

            for(int i = 0; i < jafoiBlocos; i++){
                Log.d(String.valueOf(i), String.valueOf(blocos[i]));
                blocos[i] = AritmeticaModular.calcularResto(blocos[i], tela.getE(), tela.getN());
            }



            frase = "" + blocos[0];

            for (int i = 1; i < blocos.length; i++)
            {
                if(blocos[i] != 0) frase += "-" + blocos[i];
            }

        }
        return frase;
    }

}
