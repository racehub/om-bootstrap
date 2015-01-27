#_
(:require [om-bootstrap.panel :as p])

(p/panel {:header "Collapse Me!"
          :collapsible? true}
         (d/span "Sometimes I'm here, sometimes I'm not"))