#_
(:require [om-bootstrap.button :as b]
          [om-bootstrap.random :as r])

(d/div
 (b/toolbar
  {}
  (b/button-group
   {}
   (b/button {} (r/glyphicon {:glyph "align-left"}))
   (b/button {} (r/glyphicon {:glyph "align-center"}))
   (b/button {} (r/glyphicon {:glyph "align-right"}))
   (b/button {} (r/glyphicon {:glyph "align-justify"}))))
 (b/toolbar
  {}
  (b/button-group
   {}
   (b/button {:bs-size "large"} (r/glyphicon {:glyph "star"}) " Star")
   (b/button {} (r/glyphicon {:glyph "star"}) " Star")
   (b/button {:bs-size "small"} (r/glyphicon {:glyph "star"}) " Star")
   (b/button {:bs-size "xsmall"} (r/glyphicon {:glyph "star"}) " Star"))))
