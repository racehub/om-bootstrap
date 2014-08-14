#_
(:require [om-bootstrap.button :as b])

(let [buttons (for [s ["Left" "Middle" "Right"]]
                (b/button {} s))]
  (b/toolbar {}
             (b/toolbar {} (b/button-group {:bs-size "large"} buttons))
             (b/toolbar {} (b/button-group {} buttons))
             (b/toolbar {} (b/button-group {:bs-size "small"} buttons))
             (b/toolbar {} (b/button-group {:bs-size "xsmall"} buttons))))
