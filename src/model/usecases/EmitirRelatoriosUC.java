package model.usecases;

import database.dao.InfoRelatorioDAO;
import database.dao.PassagemDAO;
import database.utils.DAO;
import model.entities.InfoLinhaTrechoRelatorio;
import model.entities.Passagem;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmitirRelatoriosUC {
    private InfoRelatorioDAO daoRelatorio;

    public EmitirRelatoriosUC(InfoRelatorioDAO daoRelatorio) {
        this.daoRelatorio = daoRelatorio;
    }

    public List<InfoLinhaTrechoRelatorio> searchInfoByInterval(Date ini, Date end){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return daoRelatorio.selectByArgs(df.format(ini), df.format(end));
    }

    public void exportToCsv(List<InfoLinhaTrechoRelatorio> tableData, String path) {
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