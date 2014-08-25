#_
(:require [om-bootstrap.random :as r]
          [om-tools.dom :as d :include-macros true])

(r/alert {:bs-style "warning"}
         (d/strong "Holy guacamole!")
         " Best check yo self, you're not looking too good.")
