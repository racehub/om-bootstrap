(ns om-bootstrap.button
  "Bootstrap buttons!"
  (:require [om.core :as om]
            [om-bootstrap.types :as t]
            [om-bootstrap.util :as u]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

;; ## Schema

(def Button
  (t/bootstrap
   {:active? s/Bool
    :disabled? s/Bool
    :block? s/Bool
    :nav-item? s/Bool
    :nav-dropdown? s/Bool}))

(def ButtonGroup
  (t/bootstrap
   {:vertical? s/Bool
    :justified? s/Bool}))

;; ## Code

(sm/defn render-anchor
  [opts :- {:classes {s/Any s/Any}
            :disabled? s/Bool
            :props {s/Any s/Any}}
   children]
  (let [props {:href (-> opts :props (:href "#"))
               :class (d/class-set (assoc (:classes opts)
                                     :disabled (:disabled? opts)))
               :role "button"}]
    (d/a (u/merge-props props (:props opts))
         children)))

(sm/defn button :- t/Component
  "Renders a button."
  [props :- Button & children]
  (let [[bs props] (t/separate Button props {:bs-class "button"
                                             :bs-style "default"
                                             :type "button"})
        klasses (if (:nav-dropdown? bs)
                  {}
                  (t/bs-class-set bs))
        klasses (merge klasses
                       {:active (:active? bs)
                        :btn-block (:block? bs)})]
    (cond
     (:nav-item? bs) (d/li {:class (d/class-set {:active (:active? bs)})}
                           (render-anchor {:props props
                                           :disabled? (:disabled? bs)
                                           :classes klasses}
                                          children))
     (or (:href props)
         (:nav-dropdown? bs))
     (render-anchor {:props props
                     :disabled? (:disabled? bs)
                     :classes klasses}
                    children)
     :else (d/button (u/merge-props props {:class (d/class-set klasses)
                                           :disabled (:disabled? bs)})
                     children))))

(sm/defn toolbar :- t/Component
  "Renders a button toolbar."
  [opts & children]
  (let [[bs props] (t/separate {} opts {:bs-class "button-toolbar"})]
    (d/div {:role "toolbar"
            :class (d/class-set (t/bs-class-set bs))}
           children)))

(sm/defn button-group :- t/Component
  "Renders the supplied children in a wrapping button-group div."
  [opts :- ButtonGroup & children]
  (let [[bs props] (t/separate ButtonGroup opts {:bs-class "button-group"})
        classes (merge (t/bs-class-set bs)
                       {:btn-group (not (:vertical? bs))
                        :btn-group-vertical (:vertical? bs)
                        :btn-group-justified (:justified? bs)})]
    (d/div (u/merge-props props {:class (d/class-set classes)})
           children)))
