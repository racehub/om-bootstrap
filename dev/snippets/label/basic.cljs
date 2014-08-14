#_
(:require [om-bootstrap.random :as r]
          [om-tools.dom :as d :include-macros true])

(d/div
 (d/h1 "Label " (r/label {} "New"))
 (d/h2 "Label " (r/label {} "New"))
 (d/h3 "Label " (r/label {} "New"))
 (d/h4 "Label " (r/label {} "New"))
 (d/h5 "Label " (r/label {} "New"))
 (d/p "Label " (r/label {} "New")))
