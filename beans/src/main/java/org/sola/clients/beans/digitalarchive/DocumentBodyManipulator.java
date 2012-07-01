/*
 * Copyright 2012 Food and Agriculture Organization of the United Nations (FAO).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sola.clients.beans.digitalarchive;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author ShresthaKabin
 */
public class DocumentBodyManipulator {
    private Icon labelIcon;
    private byte[] body;

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Icon getLabelIcon() {
        if (this.body!=null && this.body.length>1){
            try {
                this.labelIcon=new ImageIcon(this.body);
            } catch (Exception e) { }
        }
        return this.labelIcon;
    }

//<editor-fold defaultstate="collapsed" desc="commented only for reference">
//    public Icon getLabelIcon(byte[] body) {
//        if (body!=null && body.length>1){
//            try {
//                String fileName="sola_arch_"  + description;
//                int start_indx=description.lastIndexOf(".");
//                if (start_indx<0)//put extension.
//                    fileName += "." + extension;
//                labelIcon=FileUtility.getImageIcon(body, fileName);
//            } catch (Exception e) { }
//        }
//        return labelIcon;
//    }
//</editor-fold>

    private byte[] getImageByteArray(Icon labelIcon) throws IOException {
        this.labelIcon=labelIcon;
        if (labelIcon==null) return null;
        byte[] tbody;
        //original image buffer.
        BufferedImage img= new BufferedImage(labelIcon.getIconWidth()
                , labelIcon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();//.createGraphics();                 
        labelIcon.paintIcon(null, g, 0,0);// paint the Icon to the BufferedImage.
       
        //producing byte stream output.
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage)img, "png", baos);//always static type image.
        } catch (IOException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tbody= baos.toByteArray();
        baos.flush();
        baos.close();

        return tbody;
    }
    
    public void setBody(Icon labelIcon){
        if (labelIcon!=null){
            try {
                this.body= getImageByteArray(labelIcon);
            } catch (Exception e) {}
        }
        else{
            this.body=null;
        }
    }

    private byte[] getScaled_ImageByteArray(Icon labelIcon,int image_ht,int image_wdth) throws IOException {
        this.labelIcon = null;
        if (labelIcon==null) return null;
        byte[] tbody;
        //original image buffer.
        BufferedImage img= new BufferedImage(labelIcon.getIconWidth()
                , labelIcon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics g = img.createGraphics();                 
        labelIcon.paintIcon(null, g, 0,0);// paint the Icon to the BufferedImage.
        //scale image buffer.
        Image scaled_img=img.getScaledInstance(image_wdth, image_ht, Image.SCALE_DEFAULT);
        BufferedImage s_img= new BufferedImage(image_wdth,image_ht,BufferedImage.TYPE_INT_RGB);
        Graphics s_g = s_img.getGraphics();//createGraphics();
        s_g.drawImage(scaled_img, 0, 0, null);
        //producing byte stream output.
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage)s_img, "png", baos);
        } catch (IOException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        tbody= baos.toByteArray();
        baos.flush();
        baos.close();
        //resized image.
        this.labelIcon = new ImageIcon(scaled_img);
        return tbody;
    }
    
    public void setBody(Icon labelIcon,int image_ht,int image_wdth){
        if (labelIcon!=null){
            try {
                  this.body= getScaled_ImageByteArray(labelIcon,image_ht,image_wdth);
            } catch (Exception e) { }
        }
        else{
            this.body=null;
        }
    }
}
