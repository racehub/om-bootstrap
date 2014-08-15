#_
(:require [om-bootstrap.button :as b])

(b/toolbar {}
           (b/button-group {} (for [i (range 4)]
                                (b/button {} (str (inc i)))))
           (b/button-group {} (for [i (range 4 7)]
                                (b/button {} (str (inc i)))))
           (b/button-group {} (b/button {} 8)))
