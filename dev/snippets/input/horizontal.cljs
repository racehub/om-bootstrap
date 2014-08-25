#_
(:require [om-bootstrap.input :as i]
          [om-tools.dom :as d :include-macros true])

(d/form {:class "form-horizontal"}
        (i/input {:type "text" :label "Text"
                  :label-classname "col-xs-2"
                  :wrapper-classname "col-xs-10"})
        (i/input {:type "textarea" :label "Text Area"
                  :label-classname "col-xs-2"
                  :wrapper-classname "col-xs-10"})
        (i/input {:type "checkbox" :label "Checkbox"
                  :label-classname "col-xs-2"
                  :wrapper-classname "col-xs-offset-2 col-xs-10"
                  :help "Offset is applied to the wrapper."}))
