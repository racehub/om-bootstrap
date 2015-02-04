(ns om-bootstrap.tab
  (:require [om.core :as om]
            [om-bootstrap.types :as t]
            [om-bootstrap.util :as u]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

(def TabPane
  {:key (s/either s/Num s/Str)
   :title s/Str
   :content s/Any})

(def TabbedArea
  {(s/optional-key :active) (s/either s/Num s/Str)
   (s/optional-key :default) (s/either s/Num s/Str)
   (s/optional-key :animate?) s/Bool})

(defcomponentk tabbed-area* [owner]
  (init-state [_]
              {:own-active nil})
  (render [_]
          (let [{:keys [opts children]} (om/get-props owner)
                [bs props] (t/separate TabbedArea opts {:animate? true})
                own-active (om/get-state owner :own-active)
                active-tab? (fn [child] (or (= (:active bs) (:key child))
                                            (and (not (:active bs))
                                                 (= own-active (:key child)))
                                            (and (not (:active bs))
                                                 (not own-active)
                                                 (= (:default bs) (:key child)))))]
            (d/div {:role "tabpanel"}
                   (d/ul {:class "nav nav-tabs"}
                         (for [child children]
                           (d/li {:role  "presentation"
                                  :class (when (active-tab? child) "active")}
                                 (d/a {:href (str "#" (:key child))
                                       :role "tab"
                                       :data-toggle "tab"
                                       :on-click (fn [e]
                                                   (when (not (:active bs))
                                                     (om/set-state! owner :own-active (:key child)))
                                                   (.preventDefault e))}
                                      (:title child)))))
                   (d/div {:class "tab-content"}
                          (for [child children]
                            (let [active? (active-tab? child)
                                  animated? (:animate? bs)]
                              (d/div {:role  "tabpanel"
                                      :class (d/class-set {:active active?
                                                           :fade   animated?
                                                           :in     (and animated? active?)})}
                                     (:content child)))))))))

(sm/defn tabbed-area [opts :- TabbedArea & children :- [TabPane]]
  (->tabbed-area* {:opts opts :children children}))