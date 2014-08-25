#_
(:require [om-bootstrap.grid :as g]
          [om-bootstrap.input :as i])

(i/input {:label "Input wrapper"
          :help "Use this when you need something other than the
          available input types."}
         (g/row
          {}
          (g/col {:xs 6} (i/input {:type "text" :class "form-control"}))
          (g/col {:xs 6} (i/input {:type "text" :class "form-control"}))))
