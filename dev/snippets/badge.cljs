#_
(:require [om-bootstrap.random :as r]
          [om-tools.dom :as d :include-macros true])

(d/p "Badges " (r/badge {} 42))
