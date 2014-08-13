(ns om-bootstrap.nav
  (:require [om-bootstrap.types :as t]
            [om-bootstrap.util :as u]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

(def NavItem
  (t/bootstrap
   {:title s/Str
    (s/optional-key :on-select) (sm/=> s/Any s/Any)
    (s/optional-key :active?) s/Bool
    (s/optional-key :disabled?) s/Bool
    (s/optional-key :href) s/Str}))

;; TODO: Why can't I put a ref inside the anchor tag without throwing
;; an invariant violation?
(sm/defn nav-item
  "Generates a nav item for use inside of a nav element. It LOOKS like
  Key might be a required thing here."
  [opts :- NavItem & children]
  (let [[bs props] (t/separate NavItem opts {:href "#"})
        classes {:active (:active? bs)}
        handle-click (fn [e]
                       (when-let [f (:on-select bs)]
                         (.preventDefault e)
                         (when-not (:disabled? bs)
                           (f (:key props)
                              (:href bs)))))]
    (d/li (u/merge-props props {:class (d/class-set classes)})
          (d/a {:href (:href bs)
                :title (:title bs)
                :on-click handle-click}
               children))))

(def Nav
  {:bs-style (s/enum "tabs" "pills")
   :on-select (sm/=> s/Any s/Any)
   :stacked? s/Bool
   :justified? s/Bool
   :collapsible? s/Bool
   :expanded? s/Bool
   :navbar? s/Bool
   :pull-right? s/Bool})

(sm/defn nav
  [opts :- Nav & children]
  (let [[bs opts] (t/separate Nav opts {:bs-class "nav"})]
    (d/nav {:class (d/class-set (t/bs-class-set bs))}
           (d/ul {:ref "ul"
                  :class (d/class-set
                          {:nav-stacked (:stacked? bs)
                           :nav-justified (:justified? bs)
                           :navbar-nav (:navbar? bs)
                           :pull-right (:pull-right? bs)})})
           children)))
