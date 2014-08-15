#_
(:require [om-bootstrap.panel :as p]
          [om-tools.div :as d :include-macros true])

(d/div
 (p/panel {:header "Panel heading without title"}
          "Panel content")
 (p/panel {:header (d/h3 "Panel title")}
          "Panel content"))
