/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcga;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.*;  
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
/**
 *
 * @author d-precision-t1700
 */
public class Amen {
    
    
    public static void main(String[] args) throws IOException 
    {
        
        int RA = 0;
        int RB = 0;
        String A = null;
        String B=null;
        int RNA = 0;
        int RNB = 0;
        int flag;
        String[] columnDetail3 = null;
        boolean stickey = false;
        boolean blunt = false;
        boolean non_DNA=false;
        boolean[] non_DNA_main=new boolean[2];
       // String interfac=null;
       
         File file3 = new File("/home/d-precision-t1700/Amen/B-results1.txt");
        if (!file3.exists()) 
         {
          file3.createNewFile();
         }
         BufferedWriter br4 = new BufferedWriter(new FileWriter(file3));
       
        File file= new File("/home/d-precision-t1700/Amen/B-results");
        File[] files = file.listFiles();
        for(File f:files)
        {
				
	 System.out.println(f.getCanonicalPath());
         String name=f.getName();
         System.out.println(name+"  name");
         
         File file2 = new File("/home/d-precision-t1700/Amen/B-final-results/"+name+"1");
        if (!file2.exists()) 
         {
          file2.createNewFile();
         }
         BufferedWriter br3 = new BufferedWriter(new FileWriter(file2));
         
         stickey=false;
         blunt=false;
         //System.out.println(f);
         File file1=new File(f.getCanonicalPath());
         File[] files1 = file1.listFiles();
         
         
        for(File f1:files1)
        {
          System.out.println(f1.getCanonicalPath()+"  out");
          String name1=f1.getName();
          
            
				
	 non_DNA=false;
        
        
         try (BufferedReader br1 = new BufferedReader(new FileReader(f1)))
        {
            BufferedReader br2 = new BufferedReader(new FileReader(f1));
            
          //if(name1.contains("00"))
          //{
            //break;
         // }
            
           String line3;
            while ((line3 = br1.readLine()) != null)                                                            
            {
              
                 if(line3.contains("INTERFACE No"))
                 {
                     br3.write(line3+"\n");
                 }
                      
             
               // System.out.println(line3+"   line3333");
                
                 String line4 = null;
                
                 
                 
                     
                    
                     
                     if(line3.contains("Class"))
                    {
                     
                      String[] columnDetail1 = line3.split("\\|");
                      System.out.println(columnDetail1[1]+columnDetail1[2]);
                     
                      if(!columnDetail1[1].trim().equals("DNA") || !columnDetail1[2].trim().equals("DNA"))
                       {
                           non_DNA=true;
                           
                           //stickey=false;
                          // blunt=false;
                       }  
                    }
                    
                     
                     if(non_DNA==true)
                     {
                       break;
                     }  
                     
                     
                    if(line3.contains("Residues in the interface"))
                    {   
                      
                        br1.readLine();
                        String line2=br1.readLine();
                        String[] columnDetail2 = line2.split("\\|");
                        
                        System.out.println(line2+"  line2");
                        //System.out.println(columnDetail2[1].substring(0,columnDetail2[1].indexOf("(")).trim()+"  mm");
                        RA=Integer.parseInt(columnDetail2[1].substring(0,columnDetail2[1].indexOf("(")).trim());
                        RB=Integer.parseInt(columnDetail2[2].substring(0,columnDetail2[2].indexOf("(")).trim());
                        System.out.println(RA+"       "+RB);
                        
                    }
             
                     
                      RNA=0;
                      RNB=0;
                      A="";
                      B="";
                          
                   
                  
                   if(line3.contains("|") && line3.contains("[") && non_DNA==false && !line3.contains("Selection range"))
                   {
                       
                     
                   
                    columnDetail3 = line3.split("\\|");
                    System.out.println(line3+"  "+columnDetail3[2]+" c333");
                    A=columnDetail3[1].substring(1,10);
                    B=columnDetail3[3].substring(1,10);
                    System.out.println(A+"mmmm"+B);
                   
                    
                  
                    br2.mark(3);
                    
                    do
                    {
                      
                        
                        line4=br2.readLine();
                    
                     //  System.out.println(line4+ "nnnnnnnn" + RNB+"nnnnnn" +B);
                        
                    if(A!=null && line4.contains(A) && !line4.contains("[") && line4.contains("|H") )
                    {
                      
                        RNA=Integer.parseInt(line4.split("\\|")[0].trim());
                        System.out.println(line4+"line   "+RNA+"  n rna");
                        

                    }

                    if(B!=null && line4.contains(B) && !line4.contains("[") && line4.contains("|H") )
                    {

                       
                        RNB=Integer.parseInt(line4.split("\\|")[0].trim());
                        System.out.println(line4+"line   "+RNB+"  n rnb");
                       

                    }
                     
                   if(((columnDetail3[1].contains("N") && columnDetail3[3].contains("N")) || (columnDetail3[1].contains("N") && columnDetail3[3].contains("H")) || (columnDetail3[1].contains("H") && columnDetail3[3].contains("N"))) && RNA!=0 && RNB!=0)
                    {
                        if((RNA==1 && RNB==RB) ||(RNA==RA && RNB==1) || (RNA==1 && RNB==1) || (RNA==RA && RNB==RB) )
                        {
                          System.out.println( RNA+" "+RNB+" stickey");
                          br3.write(RNA+" "+RNB+" stickey"+"\n");  
                          stickey=true;
                          RNA=0;
                          RNB=0;
                          
                         
                        }
                        else if((RNA!=1 && RNB==RB) ||(RNA==RA && RNB!=1)|| (RNA!=1 && RNB==1) ||(RNA==1 && RNB!=1) || (RNA!=1 && RNB!=1 && RNA!=RA && RNB!=RB ))
                        {
                            System.out.println( RNA+" "+RNB+" blunt N-N  H-N N-H");
                            br3.write(RNA+" "+RNB+" blunt N-N  H-N N-H"+"\n");
                            blunt=true;
                            RNA=0;
                            RNB=0;
                            
                            
                           
                        }
                        else
                        {
                            stickey=false;
                            blunt=false;
                        }
                                
                    }
                    else if(((columnDetail3[1].contains("N") && columnDetail3[3].contains("O")) || (columnDetail3[1].contains("O") && columnDetail3[3].contains("N")) || (columnDetail3[1].contains("O") && columnDetail3[3].contains("O")))&& RNA!=0 && RNB!=0)
                    {
                       
                        if((RNA==1 && RNB==RB) ||(RNA==RA && RNB==1) || (RNA==1 && RNB==1) || (RNA==RA && RNB==RB))
                        {
                          System.out.println( RNA+" "+RNB+" blunt Type3");
                          br3.write(RNA+" "+RNB+" blunt Type3"+"\n");
                          blunt=true;
                          RNA=0;
                          RNB=0;
                          
                        
                        }
                        else if((RNA!=1 && RNB==RB) ||(RNA==RA && RNB!=1) || (RNA!=1 && RNB==1) ||(RNA==1 && RNB!=1) || (RNA!=1 && RNB!=1 && RNA!=RA && RNB!=RB ))
                        {
                            System.out.println( RNA+" "+RNB+" blunt N-O O-N O-O");
                            br3.write(RNA+" "+RNB+" blunt N-O O-N O-O"+"\n");
                            blunt=true;
                            RNA=0;
                            RNB=0;
                            
                            
                        }
                        else
                        {
                            stickey=false;
                            blunt=false;
                        }
                       
                        
                    }
                     
                      
                     
                    
                    }while(!line4.contains("Stats indicate"));
                    System.out.println("end of inner do");
                   
                   
                   
                    
                    if(line3.contains("[") )
                    {
                      br2.reset();
                    }
                    
                   
                    
                   
                 }  
                     
                   
                    
                 }
                 
                   br1.close();
                   br2.close();
             
            
             
            //}
        }
        }
        //if(non_DNA==true)
        //{
            
       // }
         System.out.println(non_DNA+"   non_DNA");
         System.out.println(blunt+"   bbbb");
         System.out.println(stickey+"   ssssssssss");
          if(stickey==true && blunt==false && non_DNA==false)
         {
             System.out.println("stickey");
             br3.write("stickey"+"\n");
             br4.write(name+"\tstickey"+"\n");
         }
         else if(stickey==true && blunt==true && non_DNA==false)
         {
             System.out.println("stickey+blunt");
             br3.write("stickey+blunt"+"\n");
             br4.write(name+"\tstickey\n");
         }
         else if(stickey==false && blunt==true && non_DNA==false)
         {
             System.out.println("blunt");
             br3.write("blunt"+"\n");
             br4.write(name+"\tblunt"+"\n");
         }
         else if(non_DNA==true && stickey==true )
         {
           br3.write( "stickey+ligand\n");
           br4.write(name+ "\tstickey\n");
         }
         else if(non_DNA==true &&  blunt==true )
         {
           br3.write( "blunt + ligand\n");
           br4.write(name+ "\tblunt\n");
         }
         else if(non_DNA==true && stickey==false && blunt==false )
         {
           br3.write( "ligand\n");
           br4.write(name+ "\tLigand\n");
         }
          
          br3.close();
         
      
   }
         
        br4.close();
         
    }

}
