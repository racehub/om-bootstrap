#_
(:require [om-bootstrap.input :as i]
          [om-tools.dom :as d :include-macros true])

(d/form
 (i/input {:type "text" :default-value "text"})
 (i/input {:type "password" :default-value "secret"})
 (i/input {:type "checkbox"
           :label "checkbox"
           ;; These attributes pass through to the internal input
           ;; component. :read-only is allowed instead of :readOnly
           ;; because om-tools camelcases dashed attributes.
           :checked true
           :read-only true})
 (i/input {:type "radio" :label "radio" :checked true :read-only true})
 (i/input {:type "select" :default-value "select"}
          (d/option {:value "select"} "select")
          (d/option {:value "other"} "..."))
 (i/input {:type "select" :multiple true}
          (d/option {:value "select"} "select")
          (d/option {:value "other"} "..."))
 (i/input {:type "textarea" :default-value "textarea"})
 (i/input {:type "static"} "static"))
