/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package p14;

import java.io.File;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author mario
 */
public class VentanaInternaVideo extends javax.swing.JInternalFrame {

    private EmbeddedMediaPlayer vlcplayer = null;
    private File fMedia;

    private VentanaInternaVideo(File f) {
        initComponents();
        fMedia = f;
        EmbeddedMediaPlayerComponent aVisual = new EmbeddedMediaPlayerComponent();
        getContentPane().add(aVisual, java.awt.BorderLayout.CENTER);
        vlcplayer = aVisual.getMediaPlayer();
    }
    
    public static VentanaInternaVideo getInstance(File f) {
        VentanaInternaVideo v = new VentanaInternaVideo(f);
        return (v.vlcplayer != null ? v : null);
    }
    
    public void play() {
        if (vlcplayer != null) {
            if (vlcplayer.isPlayable()) {
                //Si se estaba reproduciendo
                vlcplayer.play();
            } else {
                vlcplayer.playMedia(fMedia.getAbsolutePath());
            }
        }
    }

    public void stop() {
        if (vlcplayer != null) {
            if (vlcplayer.isPlaying()) {
                vlcplayer.pause();
            } else {
                vlcplayer.stop();
            }
        }
    }
    
    public void addMediaPlayerEventListener(MediaPlayerEventListener ml) {
        if (vlcplayer != null) {
            vlcplayer.addMediaPlayerEventListener(ml);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        setBounds(0, 0, 406, 310);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if(vlcplayer != null){
            vlcplayer.stop();
            vlcplayer = null;
        }
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}