package model.usecases;

import database.utils.DAOSelects;
import model.entities.InfoLinhaTrechoRelatorio;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmitirRelatoriosUC {

    private DAOSelects<InfoLinhaTrechoRelatorio, String> daoRelatorio;

    public EmitirRelatoriosUC(DAOSelects<InfoLinhaTrechoRelatorio, String> daoRelatorio) {
        this.daoRelatorio = daoRelatorio;
    }

    public List<InfoLinhaTrechoRelatorio> searchInfoByInterval(Date ini, Date end){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return daoRelatorio.selectByInterval(df.format(ini), df.format(end));
    }

    public void exportDailyReport(File file, Date todayDate){
        List<InfoLinhaTrechoRelatorio> infos = this.searchInfoByInterval(todayDate, todayDate);
        this.export(file, infos);
    }

    public void export(File file, List<InfoLinhaTrechoRelatorio> infos){
        if(file != null)
            this.writeToCsv(infos, file.getAbsolutePath());
    }

    public void writeToCsv(List<InfoLinhaTrechoRelatorio> tableData, String path) {
        try (OutputStreamWriter writer =
                     new OutputStreamWriter(new FileOutputStream(new File(path)), StandardCharsets.ISO_8859_1)) {
            writer.append("Data Viagem;" +
                    "Linha;" +
                    "Horário Saída;" +
                    "Trecho;" +
                    "Uso;" +
                    "Lucro R$\n");
            for (InfoLinhaTrechoRelatorio info : tableData)
                writer.append(info.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
