/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crystal_packing_interaction;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.*;  
//import org.apache.commons.lang3.Range;
//import org.apache.commons.lang3.StringUtils;
/**
 *
 * @author Intel(R) Xeon(R) CPU E5-2640 v4 (2.40GHz) 10 core processor 
 */
public class crystal {
    
    
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
        boolean sticky = false;
        boolean blunt = false;
        boolean type3A=false;
        boolean type3B=false;
        boolean non_DNA=false;
        boolean[] non_DNA_main=new boolean[2];
       // String interfac=null;
       
         File file3 = new File("/../Z-results-New.txt");
        if (!file3.exists()) 
         {
          file3.createNewFile();
         }
         BufferedWriter br4 = new BufferedWriter(new FileWriter(file3));
       
        File file= new File("/../Z-results");
        File[] files = file.listFiles();
        for(File f:files)
        {
				
	 System.out.println(f.getCanonicalPath());
         String name=f.getName();
         System.out.println(name+"  name");
         
         File file2 = new File("/../Z-final-results-new/"+name+"1");
        if (!file2.exists()) 
         {
          file2.createNewFile();
         }
         BufferedWriter br3 = new BufferedWriter(new FileWriter(file2));
         
         sticky=false;
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
                           
                           //sticky=false;
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
                          System.out.println( RNA+" "+RNB+" Type1");
                          br3.write(RNA+" "+RNB+" Type1"+"\n");  
                          sticky=true;
                          RNA=0;
                          RNB=0;
                          
                         
                        }
                        else if((RNA!=1 && RNB==RB) ||(RNA==RA && RNB!=1)|| (RNA!=1 && RNB==1) ||(RNA==1 && RNB!=1) || (RNA!=1 && RNB!=1 && RNA!=RA && RNB!=RB ))
                        {
                            System.out.println( RNA+" "+RNB+" blunt N-N  H-N N-H");
                            br3.write(RNA+" "+RNB+" Typ3A"+"\n");
                            type3A=true;
                            RNA=0;
                            RNB=0;
                            
                            
                           
                        }
                        else
                        {
                            sticky=false;
                            type3A=false;
                        }
                                
                    }
                    else if(((columnDetail3[1].contains("N") && columnDetail3[3].contains("O")) || (columnDetail3[1].contains("O") && columnDetail3[3].contains("N")) || (columnDetail3[1].contains("O") && columnDetail3[3].contains("O")))&& RNA!=0 && RNB!=0)
                    {
                       
                        if((RNA==1 && RNB==RB) ||(RNA==RA && RNB==1) || (RNA==1 && RNB==1) || (RNA==RA && RNB==RB))
                        {
                          System.out.println( RNA+" "+RNB+" blunt ");
                          br3.write(RNA+" "+RNB+" blunt "+"\n");
                          blunt=true;
                          RNA=0;
                          RNB=0;
                          
                        
                        }
                        else if((RNA!=1 && RNB==RB) ||(RNA==RA && RNB!=1) || (RNA!=1 && RNB==1) ||(RNA==1 && RNB!=1) || (RNA!=1 && RNB!=1 && RNA!=RA && RNB!=RB ))
                        {
                            System.out.println( RNA+" "+RNB+" blunt N-O O-N O-O");
                            br3.write(RNA+" "+RNB+" type3B N-O O-N O-O"+"\n");
                            type3B=true;
                            RNA=0;
                            RNB=0;
                            
                            
                        }
                        else
                        {
                            blunt=false;
                            type3B=false;
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
         System.out.println(sticky+"   ssssssssss");
         System.out.println(type3A+"   3AAAAAAAAAA");
         System.out.println(type3B+"   3BBBBBBBBBBB");
          if(sticky==true && blunt==false && non_DNA==false && type3A==false && type3B==false)
         {
             System.out.println("sticky");
             br3.write("Type1"+"\n");
             br4.write(name+"\tType1"+"\n");
         }
         else if(sticky==true && blunt==true && non_DNA==false && type3A==false && type3B==false)
         {
             System.out.println("sticky+blunt");
             br3.write("Type1+Type2"+"\n");
             br4.write(name+"\tType1\n");
         }
         else if(sticky==true && blunt==true && non_DNA==false && type3A==true && type3B==true)
         {
             System.out.println("sticky+blunt");
             br3.write("Type1+Type2+ Type3A+Type3B"+"\n");
             br4.write(name+"\tType1\n");
         }
         else if(sticky==true && blunt==true && non_DNA==false && type3A==true && type3B==false)
         {
             System.out.println("sticky+blunt");
             br3.write("Type1+Type2+ Type3A"+"\n");
             br4.write(name+"\tType1\n");
         }
         else if(sticky==true && blunt==true && non_DNA==false && type3A==false && type3B==true)
         {
             System.out.println("sticky+blunt");
             br3.write("Type1+Type2+Type3B"+"\n");
             br4.write(name+"\tType1\n");
         }
         else if(sticky==true && blunt==false && non_DNA==false && type3A==true && type3B==true)
         {
             System.out.println("sticky+blunt");
             br3.write("Type1+ Type3A+Type3B"+"\n");
             br4.write(name+"\tType1\n");
         }
         else if(sticky==true && blunt==false && non_DNA==false && type3A==false && type3B==true)
         {
             System.out.println("sticky+blunt");
             br3.write("Type1+Type3B"+"\n");
             br4.write(name+"\tType1\n");
         }
         else if(sticky==true && blunt==false && non_DNA==false && type3A==true && type3B==false)
         {
             System.out.println("sticky+blunt");
             br3.write("Type1+ Type3A"+"\n");
             br4.write(name+"\tType1\n");
         }
         else if(sticky==false && blunt==true && non_DNA==false && type3A==true && type3B==true)
         {
             System.out.println("blunt");
             br3.write("Type2+Type3A+Type3B"+"\n");
             br4.write(name+"\tType2"+"\n");
         }
         else if(sticky==false && blunt==true && non_DNA==false && type3A==true && type3B==false)
         {
             System.out.println("blunt");
             br3.write("Type2+Type3A"+"\n");
             br4.write(name+"\tType2"+"\n");
         }
         else if(sticky==false && blunt==true && non_DNA==false && type3A==false && type3B==true)
         {
             System.out.println("blunt");
             br3.write("Type2+Type3B"+"\n");
             br4.write(name+"\tType2"+"\n");
         }
         else if(sticky==false && blunt==false && non_DNA==false && type3A==true && type3B==true)
         {
             System.out.println("blunt");
             br3.write("Type3A+Type3B"+"\n");
             br4.write(name+"\tType3A"+"\n");
         }
         else if(sticky==false && blunt==false && non_DNA==false && type3A==true && type3B==false)
         {
             System.out.println("blunt");
             br3.write("Type3A"+"\n");
             br4.write(name+"\tType3A"+"\n");
         }
         else if(sticky==false && blunt==false && non_DNA==false && type3A==false && type3B==true)
         {
             System.out.println("blunt");
             br3.write("Type3B"+"\n");
             br4.write(name+"\tType3B"+"\n");
         }
         else if(non_DNA==true && sticky==true )
         {
           br3.write( "sticky+ligand\n");
           br4.write(name+ "\tType1\n");
         }
         else if(non_DNA==true && sticky==false &&  blunt==true && type3A==true && type3B==true)
         {
           br3.write( "blunt+Type3A+Type3B + ligand\n");
           br4.write(name+ "\tblunt+ligand\n");
         }
         else if(non_DNA==true && sticky==false &&  blunt==true && type3A==true && type3B==false)
         {
           br3.write( "Type2+Type3A+ligand\n");
           br4.write(name+ "\tType2+ligand\n");
         }
         else if(non_DNA==true && sticky==false &&  blunt==true && type3A==false && type3B==true)
         {
           br3.write( "Type2+Type3B+ligand\n");
           br4.write(name+ "\tType2+ligand\n");
         }
         else if(non_DNA==true && sticky==false &&  blunt==false && type3A==true && type3B==true)
         {
           br3.write( "Type3A+Type3B + ligand\n");
           br4.write(name+ "\tType3A+ligand\n");
         }
         else if(non_DNA==true && sticky==false &&  blunt==true && type3A==true && type3B==false)
         {
           br3.write( " Type3A + ligand\n");
           br4.write(name+ "\tType3A+ligand\n");
         }
         else if(non_DNA==true && sticky==false &&  blunt==true && type3A==false && type3B==true)
         {
           br3.write( " Type3B + ligand\n");
           br4.write(name+ "\tType3B+ligand\n");
         }
         else if(non_DNA==true && sticky==false && blunt==false && type3A==false && type3B==false )
         {
           br3.write( "ligand\n");
           br4.write(name+ "\tLigand\n");
         }
          
          br3.close();
         
      
   }
         
        br4.close();
         
    }

}
