#_
(:require [om-bootstrap.panel :as p])

(p/collapsible-panel {:header "Collapse Me!"}
                     (d/span "Sometimes I'm here, sometimes I'm not"))