package sk.tomsik68.ai;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import sk.tomsik68.ai.nim.NIMGameManager;

public class NIMOkno extends JFrame {
    private static final long serialVersionUID = -1741725561330033465L;
    private final JPanel contentPane;
    private NIMGameManager nim;
    private final JTabbedPane tabbedPane;
    private final JPanel panel;
    private final JPanel panel_1;
    private final JLabel lblNewLabel;
    private final JSpinner spinner;
    private final JLabel lblNewLabel_1;
    private final JList list;
    private final JSplitPane splitPane;
    private final Canvas canvas;
    private static JTextPane tpOutput;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    NIMOkno frame = new NIMOkno();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public NIMOkno() {
        setTitle("hra NIM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 768);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        panel = new JPanel();
        tabbedPane.addTab("HRA", null, panel, null);
        panel.setLayout(new BorderLayout(100, 100));

        JButton btnTah = new JButton("TAH");
        btnTah.setVerticalAlignment(SwingConstants.TOP);
        panel.add(btnTah, BorderLayout.NORTH);

        splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.4);
        panel.add(splitPane, BorderLayout.CENTER);

        canvas = new Canvas();
        splitPane.setLeftComponent(canvas);

        JScrollPane scrollPane = new JScrollPane();
        splitPane.setRightComponent(scrollPane);

        tpOutput = new JTextPane();
        scrollPane.setViewportView(tpOutput);
        btnTah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                tah();
            }
        });

        panel_1 = new JPanel();
        tabbedPane.addTab("Pravidla", null, panel_1, null);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[] { 0, 0, 0 };
        gbl_panel_1.rowHeights = new int[] { 0, 0, 0 };
        gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        panel_1.setLayout(gbl_panel_1);

        lblNewLabel = new JLabel("Paliciek na zaciatku");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        panel_1.add(lblNewLabel, gbc_lblNewLabel);

        spinner = new JSpinner();
        ((DefaultFormatter) ((JFormattedTextField) spinner.getEditor().getComponent(0)).getFormatter()).setCommitsOnValidEdit(true);
        spinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent arg0) {
                rulesChanged();
            }
        });
        spinner.setModel(new SpinnerNumberModel(new Integer(17), new Integer(1), null, new Integer(1)));
        GridBagConstraints gbc_spinner = new GridBagConstraints();
        gbc_spinner.insets = new Insets(0, 0, 5, 0);
        gbc_spinner.fill = GridBagConstraints.BOTH;
        gbc_spinner.gridx = 1;
        gbc_spinner.gridy = 0;
        panel_1.add(spinner, gbc_spinner);

        lblNewLabel_1 = new JLabel("Co mozem odobrat");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 1;
        panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);

        list = new JList();
        list.setModel(new DefaultListModel());
        ((DefaultListModel) list.getModel()).addElement("1");
        ((DefaultListModel) list.getModel()).addElement("2");
        ((DefaultListModel) list.getModel()).addElement("3");
        final JPopupMenu menu = new JPopupMenu("List");
        JMenuItem item = new JMenuItem("Add");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String s = JOptionPane.showInputDialog("Cislo: ");
                try {
                    Integer.parseInt(s);
                    ((DefaultListModel) list.getModel()).addElement(Integer.parseInt(s) + "");
                    rulesChanged();
                } catch (Exception ex) {
                } finally {
                }
            }
        });
        menu.add(item);
        item = new JMenuItem("Delete");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                ((DefaultListModel) list.getModel()).remove(list.getSelectedIndex());
                rulesChanged();
            }
        });
        menu.add(item);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    menu.show(list, e.getX(), e.getY());
                }
            }
        });
        list.add(menu);
        GridBagConstraints gbc_list = new GridBagConstraints();
        gbc_list.fill = GridBagConstraints.BOTH;
        gbc_list.gridx = 1;
        gbc_list.gridy = 1;
        panel_1.add(list, gbc_list);
        nim = new NIMGameManager(17, new TextPaneLogTarget(tpOutput), 1, 2, 3);
        Thread thread = new Thread(){
            @Override
            public void run() {
                while(isActive()){
                    render();
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    protected void rulesChanged() {
        tpOutput.setText("[ZMENA PRAVIDIEL]\n");
        int[] result = new int[list.getModel().getSize()];
        for (int i = 0; i < list.getModel().getSize(); ++i) {
            result[i] = Integer.parseInt(list.getModel().getElementAt(i).toString());
        }
        int sticks = (Integer) spinner.getValue();
        nim = new NIMGameManager(sticks, new TextPaneLogTarget(tpOutput), result);
    }

    protected void tah() {
        nim.executeTurn();
        render();
    }

    @Override
    public void repaint() {
        super.repaint();
        render();
    }
    @Override
    public void update(Graphics g) {
        super.update(g);
        repaint();
    }

    private void render() {
        Graphics gfx = canvas.getGraphics();
        gfx.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gfx.setColor(Color.white);
        gfx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gfx.setColor(Color.black);
        for (int i = 0; i < nim.getSticksLeft(); ++i) {
            gfx.drawLine(70, 20 + 15 * i, 200, 20 + 15 * i);
        }

    }

    public Canvas getCanvas() {
        return canvas;
    }

    public static void message(String msg) {
        tpOutput.setText(tpOutput.getText() + msg + "\n");
    }
}
