(ns swing.simple-app
  (:import [javax.swing JLabel]))

(import (javax.swing JButton JFrame JLabel JPanel))

(defn sample-app []
  (let [ panel (JPanel.)
         label (JLabel. "Click the button!")
         button (JButton. "Go")
         frame (JFrame. "Sample app")
         ]
    (.setOpaque panel true)
    (.add panel label)
    (.add panel button)
    (.setContentPane frame panel)
    (.setSize frame 300 100)
    (.setVisible frame true)
    )
  )

(import (javax.swing JLabel JButton JPanel JFrame))

(defn sample-app-doto []
  (doto (JFrame. "Sample Application")
    (.setContentPane (doto (JPanel.)
                       (.setOpaque true)
                       (.add (JLabel. "Click the button!"))
                       (.add (JButton. "Go"))))
    (.setSize 300 100)
    (.setVisible true)))

;
;public class SampleApp {
; public static void main(String[] args) {
;    JPanel panel = new JPanel();
;    panel.setOpaque(true);
;
;    JLabel label = new JLabel("Click the button!");
;    panel.add(label);
;
;    JButton button = new JButton("Go");
;    panel.add(button);
;
;    JFrame frame = new JFrame("Sample Application");
;    frame.setContentPane(panel);
;    frame.setSize(300, 100);
;    frame.setVisible(true);
;    }
; }