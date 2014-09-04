#_
(:require [om-bootstrap.panel :as p]
          [om-tools.dom :as d :include-macros true])

(p/panel
  {:header "List group panel"
   :list-group (d/ul {:class "list-group"}
                   (d/li {:class "list-group-item"} "Item 1")
                   (d/li {:class "list-group-item"} "Item 2")
                   (d/li {:class "list-group-item"} "Item 3"))}
  nil)
