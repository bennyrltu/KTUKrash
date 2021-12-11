package edu.ktu.ktukrash;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.source.DocumentSource;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class activity_pdf extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);


        InputStream inputStream;
        Bundle bundle = getIntent().getExtras();
        HashMap<String, String> data1 = (HashMap<String, String>) bundle.get("pdfData1");
        HashMap<String, Object> data2 = (HashMap<String, Object>) bundle.get("pdfData2");
        //Toast.makeText(this, data1.get("FP_Name"), Toast.LENGTH_LONG).show();
        //Toast.makeText(this, (String) data2.get("SP_Name"), Toast.LENGTH_LONG).show();



        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();

            PdfReader reader = new PdfReader(getAssets().open("deklaracija.pdf"));
            String outputName = path + "/" + data1.get("FP_CarNumber") + "_" + data2.get("SP_CarNumber") + ".pdf";
            PdfWriter writer = new PdfWriter(outputName);
            PdfDocument pdfDoc = new PdfDocument(reader, writer);
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            Map<String, PdfFormField> fields = form.getFormFields();

            //General info
            fields.get("data").setValue(data1.get("FP_Date")).setFontSize(12).setBorderWidth(0f);
            fields.get("laikas").setValue(data1.get("FP_Time")).setFontSize(12).setBorderWidth(0f);
            fields.get("valstybe").setValue(data1.get("Country")).setFontSize(12).setBorderWidth(0f);
            fields.get("vieta").setValue(data1.get("FP_Accident_Location")).setFontSize(12).setBorderWidth(0f);

            /*if(data1.get("FP_Injuries").equals("true") || data2.get("SP_Injuries").equals("true")){
                fields.get("suzalojimai_taip").setValue("Yes");
            }
            else{
                fields.get("suzalojimai_ne").setValue("Yes");
            }


            if(data1.get("FP_other_than_A_B").equals("true") || data2.get("SP_other_than_A_B").equals("true")){
                fields.get("zala_turtui_2").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            else {
                fields.get("zala_turtui_1").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }

            if(data1.get("FP_other_than_A_B_vehicles").equals("true") || data2.get("SP_other_than_A_B_vehicles").equals("true")){
                fields.get("zala_turtui_4").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            else{
                fields.get("zala_turtui_3").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            fields.get("liudininkai").setValue(data1.get("FP_Witness")).setFontSize(12).setBorderWidth(0f);

            //A Draudejas
            fields.get("A_draudejas_pavarde").setValue(data1.get("FP_LastName")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_draudejas_vardas").setValue(data1.get("FP_Name")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_draudejas_adresas").setValue(data1.get("FP_Location")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_draudejas_pasto_kodas").setValue(data1.get("FP_Postal")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_draudejas_valstybe").setValue(data1.get("Country")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_draudejas_telnr_elpastas").setValue(data1.get("FP_PhoneNumber")).setFontSize(12).setBorderWidth(0f);

            //A tr priemone
            fields.get("A_tp_variklis_marke_modelis").setValue(data1.get("FP_Vehicle_Maker_model")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_tp_variklis_valstybe").setValue(data1.get("FP_Vehicle_Country")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_tp_priekaba_numerio_zenklas").setValue(data1.get("FP_Trailer_Registration")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_tp_priekaba_valstybe").setValue(data1.get("FP_Trailer_Country")).setFontSize(12).setBorderWidth(0f);

            //A Draudimo imone
            fields.get("A_draudimas_pavadinimas").setValue(data1.get("FP_Insurance_Name")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_draudimas_liudijimo_nr").setValue(data1.get("FP_Insurance_Policy")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_draudimas_zalia_kortele").setValue(data1.get("FP_Insurance_Green_Card")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_draudimas_isdavusi_istaiga").setValue(data1.get("FP_Insurance_Agency")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_draudimas_adresas").setValue(data1.get("FP_Insurance_Address")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_draudimas_valstybe").setValue(data1.get("FP_Insurance_Country")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_draudimas_telnr_elpastas").setValue(data1.get("FP_Insurance_Email")).setFontSize(12).setBorderWidth(0f);
            if(data1.get("FP_DoesCover").equals("true")){
                fields.get("A_draudimas_ar_atlygintina_taip").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            else{
                fields.get("A_draudimas_ar_atlygintina_ne").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }

            //A Vairuotojas
            fields.get("A_vairuotojas_pavarde").setValue(data1.get("FP_LastName")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_vairuotojas_vardas").setValue(data1.get("FP_Name")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_vairuotojas_gimimo_data").setValue(data1.get("FP_Birthdate")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_vairuotojas_adresas").setValue(data1.get("FP_Location")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_vairuotojas_valstybe").setValue(data1.get("Country")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_vairuotojas_telnr_elpastas").setValue(data1.get("FP_Email")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_vairuotojas_pazymejimo_nr").setValue(data1.get("FP_Driving_license_no")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_vairuotojas_kategorijos").setValue(data1.get("FP_Driving_ategories")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_vairuotojas_pazymejimas_galioja_iki").setValue(data1.get("FP_License_valid_until")).setFontSize(12).setBorderWidth(0f);

            //A matomi sugadinimai
            fields.get("A_sugadinimai").setValue(data1.get("FP_Remarks_damage")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_pastabos").setValue(data1.get("FP_Remarks")).setFontSize(12).setBorderWidth(0f);

            //A CHECKBOXES
            if(data1.get("FP_1").equals("true")){
                fields.get("A_12_1").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_2").equals("true")){
                fields.get("A_12_2").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_3").equals("true")){
                fields.get("A_12_3").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_4").equals("true")){
                fields.get("A_12_4").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_5").equals("true")){
                fields.get("A_12_5").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_6").equals("true")){
                fields.get("A_12_6").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_7").equals("true")){
                fields.get("A_12_7").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_8").equals("true")){
                fields.get("A_12_8").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_9").equals("true")){
                fields.get("A_12_9").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_10").equals("true")){
                fields.get("A_12_10").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_11").equals("true")){
                fields.get("A_12_11").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_12").equals("true")){
                fields.get("A_12_12").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_13").equals("true")){
                fields.get("A_12_13").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_14").equals("true")){
                fields.get("A_12_14").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_15").equals("true")){
                fields.get("A_12_15").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_16").equals("true")){
                fields.get("A_12_16").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data1.get("FP_17").equals("true")){
                fields.get("A_12_17").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }

            //B draudejas
            fields.get("B_draudejas_pavarde").setValue(data2.get("SP_LastName").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_draudejas_vardas").setValue(data2.get("SP_Name").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_draudejas_adresas").setValue(data2.get("SP_Location").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_draudejas_valstybe").setValue(data1.get("Country")).setFontSize(12).setBorderWidth(0f);
            fields.get("B_draudejas_telnr_elpastas").setValue(data2.get("SP_Email").toString()).setFontSize(12).setBorderWidth(0f);

            //B transporto priemone
            fields.get("B_tp_variklis_marke_modelis").setValue(data2.get("SP_Vehicle_Maker_model").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_tp_variklis_valstybe").setValue(data2.get("SP_Vehicle_Country").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_tp_priekaba_numerio_zenklas").setValue(data2.get("SP_Trailer_Registration").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_tp_priekaba_valstybe").setValue(data2.get("SP_Trailer_Country").toString()).setFontSize(12).setBorderWidth(0f);

            //B Draudimo imone
            fields.get("B_draudimas_pavadinimas").setValue(data2.get("SP_Insurance_Name").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_draudimas_liudijimo_nr").setValue(data2.get("SP_Insurance_Policy").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_draudimas_zalia_kortele").setValue(data2.get("SP_Insurance_Green_Card").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_draudimas_isdavusi_istaiga").setValue(data2.get("SP_Insurance_Agency").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_draudimas_adresas").setValue(data2.get("SP_Insurance_Address").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_draudimas_valstybe").setValue(data2.get("SP_Insurance_Country").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_draudimas_telnr_elpastas").setValue(data2.get("SP_Insurance_Email").toString()).setFontSize(12).setBorderWidth(0f);
            if(data2.get("SP_DoesCover").toString().equals("true")){
                fields.get("B_draudimas_ar_atlygintina_taip").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            else{
                fields.get("B_draudimas_ar_atlygintina_ne").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }

            //B vairuotojas
            fields.get("B_vairuotojas_pavardes").setValue(data2.get("SP_LastName").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_vairuotojas_vardas").setValue(data2.get("SP_Name").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_vairuotojas_gimimo_data").setValue(data2.get("SP_Birthdate").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_vairuotojas_adresas").setValue(data2.get("SP_Location").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_vairuotojas_valstybe").setValue(data1.get("Country")).setFontSize(12).setBorderWidth(0f);
            fields.get("B_vairuotojas_telnr_elpastas").setValue(data2.get("SP_Email").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_vairuotojas_pazymejimo_nr").setValue(data2.get("SP_Driving_license_no").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_vairuotojas_kategorijos").setValue(data2.get("SP_Driving_ategories").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_vairuotojas_pazymejimas_galioja_iki").setValue(data2.get("SP_License_valid_until").toString()).setFontSize(12).setBorderWidth(0f);

            //B Checkboxes
            if(data2.get("SP_1").toString().equals("true")){
                fields.get("B_12_1").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_2").toString().equals("true")){
                fields.get("B_12_2").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_3").toString().equals("true")){
                fields.get("B_12_3").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_4").toString().equals("true")){
                fields.get("B_12_5").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_6").toString().equals("true")){
                fields.get("B_12_6").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_7").toString().equals("true")){
                fields.get("B_12_7").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_8").toString().equals("true")){
                fields.get("B_12_8").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_9").toString().equals("true")){
                fields.get("B_12_9").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_10").toString().equals("true")){
                fields.get("B_12_10").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_11").toString().equals("true")){
                fields.get("B_12_11").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_12").toString().equals("true")){
                fields.get("B_12_12").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_13").toString().equals("true")){
                fields.get("B_12_13").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_14").toString().equals("true")){
                fields.get("B_12_14").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_15").toString().equals("true")){
                fields.get("B_12_15").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_16").toString().equals("true")){
                fields.get("B_12_16").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }
            if(data2.get("SP_17").toString().equals("true")){
                fields.get("B_12_17").setValue("Yes").setFontSize(12).setBorderWidth(0f);
            }

            //B sugadinimai
            fields.get("B_sugadinimai").setValue(data2.get("SP_Remarks_damage").toString()).setFontSize(12).setBorderWidth(0f);
            //B pastabos
            fields.get("B_pastabos").setValue(data2.get("SP_Remarks").toString()).setFontSize(12).setBorderWidth(0f);*/

            //form.flattenFields();
            pdfDoc.close();


            //perdaryti nes fieldu nerodo
            PDFView myPdf = (PDFView) findViewById(R.id.pdfView);
            File file = new File(outputName);
            myPdf.fromFile(file).load();

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}