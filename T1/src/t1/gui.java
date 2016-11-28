/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adams
 */
public class gui extends javax.swing.JFrame
{

    private final ConexaoSQL sql = new ConexaoSQL();
    public Connection con;
    private JTable tabela;

    /**
     * Creates new form gui
     */
    public gui()
    {
        initComponents();
        this.setSize(665, 210);
        jSeparator1.setVisible(false);
        jScrollPaneResultados.setVisible(false);
        jCombo.setVisible(false);
        jImagem.setVisible(false);
        jOK.setVisible(false);
        jTxtCampoBusca.setLineWrap(true);
        jTxtCampoBusca.setWrapStyleWord(true);
        jTxtID.setVisible(false);
        con = sql.connect("postgres", "123");
        
        
       

        //generateTestsStr();//Debug

    }

    
    
    private void exportTable(JTable table)
    {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);
        String path = null;
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            path = file.getPath(); // Obtém o path para salvar
        }
        try
        {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(path + ".pdf"));
            doc.open();
            doc.addTitle(jTxtCampoBusca.getText());
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            //adding table headers
            for (int i = 0; i < table.getColumnCount(); i++)
            {
                pdfTable.addCell(table.getColumnName(i));
            }
            //extracting data from the JTable and inserting it to PdfPTable
            for (int rows = 0; rows < table.getRowCount(); rows++)
            {
                for (int cols = 0; cols < table.getColumnCount(); cols++)
                {
                    pdfTable.addCell(table.getModel().getValueAt(rows, cols).toString());

                }
            }
            doc.add(pdfTable);
            doc.close();
            System.out.println("[INFO]\tTabela exportada.");
        } catch (DocumentException ex)
        {
            Logger.getLogger(T1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(T1.class.getName()).log(Level.SEVERE, null, ex);
        }
        //jTxtCampoBusca.setLineWrap(true);
        //jTxtCampoBusca.setWrapStyleWord(true);
        //con = sql.connect("postgres", "123");

        //generateTestsStr();//Debug
    }

    private void generateTestsStr()
    {
        String command = null;

        //CS_EU
        ArrayList<String> desc = new ArrayList<>();
        desc.add("CS_EU");
        desc.add("CS_CB");

        desc.add("CL_EU");
        desc.add("CL_CB");

        desc.add("SC_EU");
        desc.add("SC_CB");

        desc.add("CT_EU");
        desc.add("CT_CB");

        desc.add("ZK_EU");
        desc.add("ZK_CB");

        //kNN
        String idVal = "1";
        for (String d : desc)
        {
            command = "SELECT id, dist('mirflickr', 'complex_data', '" + d + "', complex_data_id, "
                    + "("
                    + "SELECT fem_extraction('mirflickr', 'complex_data', '" + d + "',"
                    + "("
                    + "SELECT complex_data "
                    + "FROM mirflickr "
                    + "WHERE id = " + idVal
                    + "))"
                    + ")) AS distancia "
                    + "FROM mirflickr "
                    + "ORDER BY distancia "
                    + "LIMIT 10;";
            System.out.println(command);
        }

        //Range
        idVal = "1";
        for (String d : desc)
        {
            command = "SELECT id, distancia "
                    + "FROM ( "
                    + "SELECT id, dist('mirflickr', 'complex_data', '" + d + "', complex_data_id, "
                    + "("
                    + "SELECT fem_extraction('mirflickr', 'complex_data', '" + d + "',"
                    + "("
                    + "SELECT complex_data "
                    + "FROM mirflickr "
                    + "WHERE id = " + idVal
                    + "))"
                    + ")) AS distancia "
                    + "FROM mirflickr"
                    + ") AS Range "
                    + "WHERE distancia <= 1;";
            System.out.println(command);
        }
    }

    private void showResult(Vector data, Vector columnNames, Vector images)
    {
        DefaultTableModel d = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(d);
        tabela = new JTable(d);
        jScrollPaneResultados.setViewportView(table);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                int row = table.getSelectedRow();
                int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
                ImageIcon icon = (ImageIcon) images.elementAt(row);
                ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 320, 320));
                jImagem.setIcon(thumbnailIcon);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBtBuscar = new javax.swing.JButton();
        jBtUpload = new javax.swing.JButton();
        jLabelImgConsulta = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTxtCampoBusca = new javax.swing.JTextArea();
        jScrollPaneResultados = new javax.swing.JScrollPane();
        jCombo = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jOK = new javax.swing.JButton();
        jImagem = new javax.swing.JLabel();
        jStatus = new javax.swing.JLabel();
        jTxtID = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jBtBuscar.setText("Buscar");
        jBtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtBuscarActionPerformed(evt);
            }
        });

        jBtUpload.setText("Upload ");
        jBtUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtUploadActionPerformed(evt);
            }
        });

        jLabelImgConsulta.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTxtCampoBusca.setColumns(20);
        jTxtCampoBusca.setRows(5);
        jTxtCampoBusca.setText("Digite aqui sua busca");
        jScrollPane1.setViewportView(jTxtCampoBusca);

        jButton1.setText("Exportar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jOK.setText("OK");
        jOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOKActionPerformed(evt);
            }
        });

        jImagem.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jStatus.setText("Status: conectado");

        jTxtID.setText("Id para comparar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelImgConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jStatus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jOK)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelImgConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtUpload)
                            .addComponent(jBtBuscar)
                            .addComponent(jButton1)
                            .addComponent(jStatus))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPaneResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jOK)
                    .addComponent(jTxtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtBuscarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBtBuscarActionPerformed
    {//GEN-HEADEREND:event_jBtBuscarActionPerformed
        if (jTxtCampoBusca.getText().equals("debug"))
        {
            jCombo.setVisible(true);
            jOK.setVisible(true);
            jTxtID.setVisible(true);
//----------------KNN
//----------------COLOR LAYOUT
            jCombo.addItem("knn Color Layout + Euclidean");

            jCombo.addItem("knn Color Layout + Manhattan");

//----------------SCALABLE COLOR
            jCombo.addItem("knn Scalable Color + Euclidean");

            jCombo.addItem("knn Scalable Color + Manhattan");
//----------------COLOR STRUCTURE
            jCombo.addItem("knn Color Structure + Euclidean");

            jCombo.addItem("knn Color Structure + Manhattan");
//----------------COLOR TEMPERATURE
            jCombo.addItem("knn Color Temperature + Euclidean");

            jCombo.addItem("knn Color Temperature + Manhattan");
//----------------ZERNIKE
            jCombo.addItem("knn Zernike + Euclidean");

            jCombo.addItem("knn Zernike + Manhattan");

//----------------range
//----------------COLOR LAYOUT
            jCombo.addItem("range Color Layout + Euclidean");

            jCombo.addItem("range Color Layout + Manhattan");

//----------------SCALABLE COLOR
            jCombo.addItem("range Scalable Color + Euclidean");

            jCombo.addItem("range Scalable Color + Manhattan");
//----------------COLOR STRUCTURE
            jCombo.addItem("range Color Structure + Euclidean");

            jCombo.addItem("range Color Structure + Manhattan");
//----------------COLOR TEMPERATURE
            jCombo.addItem("range Color Temperature + Euclidean");

            jCombo.addItem("range Color Temperature + Manhattan");
//----------------ZERNIKE
            jCombo.addItem("range Zernike + Euclidean");

            jCombo.addItem("range Zernike + Manhattan");
            this.setSize(990, 600);
            //this.setSize(950, 340);
            return;
        }

        jSeparator1.setVisible(true);
        jScrollPaneResultados.setVisible(true);
        jImagem.setVisible(true);
        jImagem.setSize(320, 320);
        
        
        //this.setSize(950, 340);
        this.setSize(990, 600);
        this.repaint();
        int i = 0;
        int colunas;
        String command;
        command = jTxtCampoBusca.getText();
        Statement stmt = null;
        ResultSet rs;
        Vector data = new Vector();
        Vector columnNames = new Vector();
        Vector images = new Vector();

        try
        {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);//necessário para reposicionar a leitura apos a contagem de resultados
            rs = stmt.executeQuery(command);
            ResultSetMetaData metadados = rs.getMetaData();
            colunas = metadados.getColumnCount();

            while (rs.next())
            { //verifica se algum resultado retornou nulo
                i++;
            }
            if (i == 0)
            {
                jStatus.setText("[INFO]\t Nenhum resultado encontrado.");
                //rs.close();
            }else{
                jStatus.setText("[INFO]\t " + i + " resultado(s) encontrado(s).");
            }
            
            for (i = 1; i <= colunas; i++)
            {
                columnNames.addElement(metadados.getColumnName(i)); //adiciona os nomes das colunas ao vetor de nomes
            }
            rs.beforeFirst(); //reposiciona leitura
            while (rs.next())
            {
                Vector row = new Vector(colunas);
                for (i = 1; i <= colunas; i++)
                {
                    row.addElement(rs.getObject(i));
                }
                data.addElement(row);
                ImageIcon icon = new ImageIcon(getImageFromID(rs.getInt("id")));
                images.add(icon);
            }
            rs.close();

        } catch (SQLException ex)
        {
            Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        showResult(data, columnNames, images);
    }//GEN-LAST:event_jBtBuscarActionPerformed

    private Image getScaledImage(Image srcImg, int w, int h)
    {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    private void jBtUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtUploadActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();

            try
            {
                String path = null;
                path = file.getPath(); // Obtém o path da imagem

                ImageIcon icon = new ImageIcon(path);

                //Redimensiona a imagem
                ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 120, 120));
                jLabelImgConsulta.setIcon(thumbnailIcon);

                String commandImportImg = "SELECT id,dist('mirflickr', 'complex_data', 'CS_EU', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CS_EU', (SELECT image_import('" + path + "'))))) AS distancia FROM mirflickr ORDER BY distancia LIMIT 10;";

                jTxtCampoBusca.setText(commandImportImg);
            } catch (Exception ex)
            {
                jStatus.setText("[ERRO]\tProblema ao acessar o arquivo " + file.getAbsolutePath());
            }
        } else
        {
            jStatus.setText("[DEBUG]\tAcesso ao arquivo cancelado pelo usuário.");
        }
    }//GEN-LAST:event_jBtUploadActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        exportTable(tabela);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jOKActionPerformed
    {//GEN-HEADEREND:event_jOKActionPerformed
        
        int id = Integer.parseInt(jTxtID.getText());
        try
        {
            //Image image = getImageFromID(id);
            
            ImageIcon image = new ImageIcon(getFilenameFromID(id));
            ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(image.getImage(), 120, 120));
            jLabelImgConsulta.setIcon(thumbnailIcon);
            
        } catch (SQLException ex)
        {
            Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch (jCombo.getSelectedIndex())
        {
//--------------knn
//--------------COLOR LAYOUT

            case 0:
                jTxtCampoBusca.setText("SELECT id, dist('mirflickr', 'complex_data', 'CL_EU', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CL_EU',(SELECT complex_data FROM mirflickr WHERE id = " + id +")))) AS distancia FROM mirflickr ORDER BY distancia LIMIT 10;");
                break;

            case 1:
                jTxtCampoBusca.setText("SELECT id, dist('mirflickr', 'complex_data', 'CL_CB', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CL_CB',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr ORDER BY distancia LIMIT 10;");
                break;

//----------------SCALABLE COLOR
            case 2:
                jTxtCampoBusca.setText("SELECT id, dist('mirflickr', 'complex_data', 'SC_EU', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'SC_EU',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr ORDER BY distancia LIMIT 10;");
                break;

            case 3:
                jTxtCampoBusca.setText("SELECT id, dist('mirflickr', 'complex_data', 'SC_CB', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'SC_CB',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr ORDER BY distancia LIMIT 10;");
                break;
//----------------COLOR STRUCTURE
            case 4:
                jTxtCampoBusca.setText("SELECT id, dist('mirflickr', 'complex_data', 'CS_EU', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CS_EU',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr ORDER BY distancia LIMIT 10;");
                break;

            case 5:
                jTxtCampoBusca.setText("SELECT id, dist('mirflickr', 'complex_data', 'CS_CB', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CS_CB',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr ORDER BY distancia LIMIT 10;");
                break;
//----------------COLOR TEMPERATURE
            case 6:
                jTxtCampoBusca.setText("SELECT id, dist('mirflickr', 'complex_data', 'CT_EU', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CT_EU',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr ORDER BY distancia LIMIT 10;");
                break;

            case 7:
                jTxtCampoBusca.setText("SELECT id, dist('mirflickr', 'complex_data', 'CT_CB', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CT_CB',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr ORDER BY distancia LIMIT 10;");
                break;
//----------------ZERNIKE
            case 8:
                jTxtCampoBusca.setText("SELECT id, dist('mirflickr', 'complex_data', 'ZK_EU', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'ZK_EU',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr ORDER BY distancia LIMIT 10;");
                break;

            case 9:
                jTxtCampoBusca.setText("SELECT id, dist('mirflickr', 'complex_data', 'ZK_CB', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'ZK_CB',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr ORDER BY distancia LIMIT 10;");
                break;

//--------------range
//--------------COLOR LAYOUT                
            case 10:
                jTxtCampoBusca.setText("SELECT id, distancia FROM ( SELECT id, dist('mirflickr', 'complex_data', 'CL_EU', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CL_EU',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr) AS Range WHERE distancia <= 1 ORDER BY distancia;");
                break;

            case 11:
                jTxtCampoBusca.setText("SELECT id, distancia FROM ( SELECT id, dist('mirflickr', 'complex_data', 'CL_CB', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CL_CB',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr) AS Range WHERE distancia <= 1 ORDER BY distancia;");
                break;

//----------------SCALABLE COLOR
            case 12:
                jTxtCampoBusca.setText("SELECT id, distancia FROM ( SELECT id, dist('mirflickr', 'complex_data', 'SC_EU', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'SC_EU',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr) AS Range WHERE distancia <= 1 ORDER BY distancia;");
                break;

            case 13:
                jTxtCampoBusca.setText("SELECT id, distancia FROM ( SELECT id, dist('mirflickr', 'complex_data', 'SC_CB', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'SC_CB',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr) AS Range WHERE distancia <= 1 ORDER BY distancia;");
                break;
//----------------COLOR STRUCTURE
            case 14:
                jTxtCampoBusca.setText("SELECT id, distancia FROM ( SELECT id, dist('mirflickr', 'complex_data', 'CS_EU', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CS_EU',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr) AS Range WHERE distancia <= 1 ORDER BY distancia;");
                break;

            case 15:
                jTxtCampoBusca.setText("SELECT id, distancia FROM ( SELECT id, dist('mirflickr', 'complex_data', 'CS_CB', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CS_CB',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr) AS Range WHERE distancia <= 1 ORDER BY distancia;");
                break;
//----------------COLOR TEMPERATURE
            case 16:
                jTxtCampoBusca.setText("SELECT id, distancia FROM ( SELECT id, dist('mirflickr', 'complex_data', 'CT_EU', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CT_EU',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr) AS Range WHERE distancia <= 1 ORDER BY distancia;");
                break;

            case 17:
                jTxtCampoBusca.setText("SELECT id, distancia FROM ( SELECT id, dist('mirflickr', 'complex_data', 'CT_CB', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'CT_CB',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr) AS Range WHERE distancia <= 1 ORDER BY distancia;");
                break;
//----------------ZERNIKE
            case 18:
                jTxtCampoBusca.setText("SELECT id, distancia FROM ( SELECT id, dist('mirflickr', 'complex_data', 'ZK_EU', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'ZK_EU',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr) AS Range WHERE distancia <= 1 ORDER BY distancia;");
                break;

            case 19:
                jTxtCampoBusca.setText("SELECT id, distancia FROM ( SELECT id, dist('mirflickr', 'complex_data', 'ZK_CB', complex_data_id, (SELECT fem_extraction('mirflickr', 'complex_data', 'ZK_CB',(SELECT complex_data FROM mirflickr WHERE id = " + id + ")))) AS distancia FROM mirflickr) AS Range WHERE distancia <= 1 ORDER BY distancia;");
                break;
        }
    }//GEN-LAST:event_jOKActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                gui frame = new gui();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }

    private String getFilenameFromID(int id) throws SQLException
    {
        Statement stmt;
        ResultSet rs = null;
        stmt = con.createStatement();
        rs = stmt.executeQuery("SELECT filename FROM MIRFLICKR WHERE ID=" + id + ";");
        String filename = null;
        
        while (rs.next())
        {
            filename = rs.getString("filename"); //cria lista de imagens na ordem que foram adicionadas à tabela
        }

        return filename;

    }
    
    private Image getImageFromID(int id) throws SQLException
    {
        Statement stmt;
        ResultSet rs = null;
        stmt = con.createStatement();
        rs = stmt.executeQuery("SELECT COMPLEX_DATA FROM MIRFLICKR WHERE ID=" + id + ";");
        byte[] imgData = null;
        Image img = null;
        while (rs.next())
        {
            imgData = rs.getBytes(1); //cria lista de imagens na ordem que foram adicionadas à tabela
            img = Toolkit.getDefaultToolkit().createImage(imgData);
        }

        return img;

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtBuscar;
    private javax.swing.JButton jBtUpload;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jCombo;
    private javax.swing.JLabel jImagem;
    private javax.swing.JLabel jLabelImgConsulta;
    private javax.swing.JButton jOK;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneResultados;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel jStatus;
    private javax.swing.JTextArea jTxtCampoBusca;
    private javax.swing.JTextField jTxtID;
    // End of variables declaration//GEN-END:variables
}
