package com.project;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * Piston.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Piston implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Piston());
        frame.add(canvas);
        frame.setSize(720, 540);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }
    private float view_rotx = 0.0f;
    private float view_roty = 0.0f;
    private int oldMouseX;
    private int oldMouseY;


    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);
        
        // supaya depth dirender dengan benar
        gl.glEnable(GL.GL_DEPTH_TEST);

        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    float angle = 0;

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" around
        // atur kamera
        GLU glu = new GLU();
        glu.gluLookAt(
                0, 0, 15, // koordinat posisi kamera
                0, 0, 0, // koordinat arah kamera (kamera menghadap ke mana)
                0, 1, 0 // koordinat arah atas kamera
        );
        
        gl.glRotatef(angle++, 1.0f, 0.0f, 0.0f);
        // Drawing Using Triangles

        gl.glTranslatef(0.0f, 3.0f, 0.0f);
        Part.pistonHead(gl);
//        Part.pistonStem(gl);
        gl.glTranslatef(0.0f, -8.0f, 0.0f);
        Part.pistonStem(gl);
        Part.sirip(gl);
        gl.glPushMatrix();
        gl.glTranslatef(1f, 3f, 0.0f);
        Part.sirip_banyak(gl);
        gl.glPopMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        Part.balok(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        Part.balok_tambahan_atas(gl);
        gl.glPopMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        Part.body_bandul(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        Part.sambungan_bandul(gl);
        gl.glPopMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        Part.sisi_samping_sambungan_bandul(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        Part.bandul(gl);
        gl.glPopMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        Part.ring(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        Part.bandul(gl);
        gl.glPopMatrix();
        gl.glFlush();
    }
static boolean b = false;
    static boolean i = false;

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        //To change body of generated methods, choose Tools | Templates.
    }

    public void mouseClicked(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    public void mousePressed(MouseEvent e) {
        oldMouseX = e.getX();
        oldMouseY = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    public void mouseEntered(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    public void mouseExited(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Dimension size = e.getComponent().getSize();
        float thetaY = 360.0f * ((float) (x - oldMouseX) / (float) size.width);
        float thetaX = 360.0f * ((float) (y - oldMouseY) / (float) size.height);
        oldMouseX = x;
        oldMouseY = y;
        view_rotx += thetaX;
        view_roty += thetaY;
    }

    public void mouseMoved(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    public void keyTyped(KeyEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == 32) {
            b = true;//To change body of generated methods, choose Tools | Templates.
        } else if (e.getKeyCode() == 39) {
            i = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

}

}
