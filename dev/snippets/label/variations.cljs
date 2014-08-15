#_
(:require [om-bootstrap.random :as r]
          [om-tools.dom :as d :include-macros true])

(d/div
 (r/label {:bs-style "default"} "Default")
 (r/label {:bs-style "primary"} "Primary")
 (r/label {:bs-style "success"} "Success")
 (r/label {:bs-style "info"} "Info")
 (r/label {:bs-style "warning"} "Warning")
 (r/label {:bs-style "danger"} "Danger"))
