package gui

import java.awt.Dimension
import java.net.URL
import javax.swing.*

class Window() : JFrame() {

    private val minSz = Dimension(500, 600)

    val regHref = Regex(
        "(?:(?:https?|ftp|file):\\/\\/|www\\.|ftp\\.)(?:\\([-A-Z0-9+&@#\\/%=~_|\$?!:,.]*\\)|[-A-Z0-9+&@#\\/%=~_|\$?!:,.])*(?:\\([-A-Z0-9+&@#\\/%=~_|\$?!:,.]*\\)|[A-Z0-9+&@#\\/%=~_|\$])",
        RegexOption.MULTILINE
    )

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = minSz


        val urlArea = JTextArea()
        urlArea.apply {
            maximumSize = Dimension(minSz.width / 2, 80)
            minimumSize = Dimension(100, urlArea.maximumSize.height / 2)
            text = ""
        }

        val textPane = JTextPane().apply {
            contentType = "text/html"
            text = if(!urlArea.text.isEmpty())
                URL(urlArea.text).readText()
            else return@apply
        }

        val buttonLoad = JButton("Load!").apply {
            addActionListener() {
                val txt = URL(urlArea.text).readText()
                val txtL = "<".toRegex().replace(txt){
                    "&lt;"
                }
                 val txtR = ">".toRegex().replace(txtL){
                     "&gt;"
                 }
                textPane.text = regHref.replace(txt) {
                    "<a href=\"${it.value}\">${it.value}</a>"
                }
            }
        }


        val scrPane = JScrollPane(textPane) //для скролла

        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(urlArea, 400, SHRINK, SHRINK)
                            .addComponent(scrPane, 400, 1080, GROW)
                            .addComponent(buttonLoad, SHRINK, SHRINK, SHRINK)
                    )
                    .addGap(8)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addComponent(urlArea, SHRINK, SHRINK, SHRINK)
                    .addGap(8)
                    .addComponent(scrPane, 400, 1920, GROW)
                    .addGap(8)
                    .addComponent(buttonLoad, SHRINK, SHRINK, SHRINK)
                    .addGap(8)
            )
        }
    }

    companion object {
        const val GROW = GroupLayout.DEFAULT_SIZE
        const val SHRINK = GroupLayout.PREFERRED_SIZE
    }
}