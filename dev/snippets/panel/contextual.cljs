#_
(:require [om-bootstrap.panel :as p]
          [om-bootstrap.dom :as d :include-macros true])

(for [style [nil "primary" "success" "info" "warning" "danger"]]
  (p/panel (merge {:header (d/h3 "Panel title")}
                  (when style {:bs-style style}))
           "Panel content"))
