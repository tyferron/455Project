package csci455.project.chatroom.client.GUI;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Item_Left extends JLayeredPane {

    public Item_Left(String user, String text) {
        initComponents();
        txt.setText(text);
        float hue = 0.0F;
        for(char c : user.toCharArray()) { hue+=c; }
        txt.setBgColor(new Color(Color.HSBtoRGB((hue%75)/75.0F, 0.8F, 1F)));
        jLabel1.setText(user);
        jLabel1.setToolTipText(user);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt = new Chat_Text();
        jLabel1 = new JLabel();

        txt.setEditable(false);
        txt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        txt.setBgColor(new Color(221, 246, 255));
        txt.setBorderColor(new Color(6, 126, 183));

        jLabel1.setIcon(new ImageIcon(getClass().getResource("/csci455/project/chatroom/client/GUI/user.png"))); // NOI18N
        jLabel1.setToolTipText("");

        setLayer(txt, JLayeredPane.DEFAULT_LAYER);
        setLayer(jLabel1, JLayeredPane.DEFAULT_LAYER);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(txt, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(0, 0, 0))
            );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel jLabel1;
    private Chat_Text txt;
    // End of variables declaration//GEN-END:variables
}
