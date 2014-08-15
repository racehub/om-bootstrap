#_
(:require [om-bootstrap.random :as r]
          [om-tools.dom :as d :include-macros true])

(r/page-header {} "Example page header "
               (d/small "Subtext for header"))
