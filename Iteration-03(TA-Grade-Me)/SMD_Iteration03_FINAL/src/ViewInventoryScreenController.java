import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewInventoryScreenController implements ActionListener {

    private ViewInventoryScreenView view;
    private SQLDataAdapter adapter;

    public ViewInventoryScreenController(ViewInventoryScreenView viewIn, SQLDataAdapter adapterIn) {
        view = viewIn;
        adapter = adapterIn;
        populate();

        view.getBackButton().addActionListener(this);
        view.getAddProductButton().addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBackButton()) {
            Application.getInstance().getViewInventoryScreenView().setVisible(false);
            Application.getInstance().getInventoryScreenView().setVisible(true);
            return;
        } else if (e.getSource() == view.getAddProductButton()) {
            Application.getInstance().getViewInventoryScreenView().setVisible(false);
            Application.getInstance().getProductScreenView().setVisible(true);
        }
        //Other buttons are created with separate action listeners because they are not distinct and are generated procedurally.
    }

    //
    // Returns an array of JPanels that each correspond to a different item in the inventory.
    //
    public void populate() {
        Product[] productList = adapter.getAll();
        JPanel superPanel = new JPanel(); //Allows the creation of a scroll section for the product part only.

        for (int i = 0; i < productList.length; i++)
        {
            JPanel productPanel = new JPanel();

            String itemName = productList[i].getName();
            JButton itemButton = new JButton(itemName);
            int currentIndex = i; // Allows i to be passed on to the action listener. Because i normally changes you have to recreate it.

            int[] buttonDimensions = view.getButtonDimensions();
            itemButton.setPreferredSize(new Dimension(buttonDimensions[0], buttonDimensions[1]));

            itemButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int id =  productList[currentIndex].getID();
                    Application.getInstance().getProductScreenView().getProductIDText().setText(String.valueOf(id));
                    Application.getInstance().getProductScreenController().loadProduct();
                    Application.getInstance().getProductScreenView().setVisible(true);
                    Application.getInstance().getViewInventoryScreenView().setVisible(false);
                }
            });

            JButton removeButton = new JButton("Remove");
            removeButton.setPreferredSize(new Dimension(buttonDimensions[0], buttonDimensions[1]));
            removeButton.setBackground(Color.pink);
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    adapter.remove(productList[currentIndex].getID());
                    resetView();
                }
            });

            //Add stuff to panel
            productPanel.add(itemButton);
            productPanel.add(removeButton);

            //Add stuff to actual view
            superPanel.add(productPanel);
        }
        superPanel.setLayout(new BoxLayout(superPanel, BoxLayout.Y_AXIS));
        JScrollPane jsp = new JScrollPane(superPanel);
        view.add(jsp);
    }

    //
    // The view is reset whenever something is changed so that it stays up to date. As a result the controller is also reset.
    // Garbage collection in Java will get rid of anything leftover.
    //
    public void resetView() {
        Application.getInstance().getViewInventoryScreenView().setVisible(false);
        Application.getInstance().setViewInventoryScreenView(new ViewInventoryScreenView());
        Application.getInstance().setViewInventoryScreenController(new ViewInventoryScreenController(Application.getInstance().getViewInventoryScreenView(), adapter));
        Application.getInstance().getViewInventoryScreenView().setVisible(true);
    }
}
