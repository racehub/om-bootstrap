#_
(:require [om-bootstrap.random :as r]
          [om-tools.dom :as d :include-macros true])

(d/div
 (r/well {:bs-size "large"} "Look, I'm in a large well!")
 (r/well {:bs-size "small"} "Look, I'm in a small well!"))
