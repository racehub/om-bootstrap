#_
(:require [om-bootstrap.button :as b]
          [om-bootstrap.random :as r]
          [om-tools.dom :as d :include-macros true])

(r/jumbotron {}
             (d/h1 "Hello, World!")
             (d/p "This is a simple hero unit, a simple
             jumbotron-style component for calling extra attention to
             featured content or information.")
             (d/p (b/button {:bs-style "primary"} "Learn More")))
