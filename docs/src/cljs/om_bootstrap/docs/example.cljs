(ns om-bootstrap.docs.example
  (:require [om.core :as om :include-macros true]
            [om-bootstrap.util :as u]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true]))

(defn bs-example
  ([item] (d/div {:class "bs-example"} item))
  ([props item]
     (d/div (u/merge-props props {:class "bs-example"})
            item)))

(defcomponentk code-block
  "Generates a component"
  [[:data code {language "clojure"}] owner]
  (did-mount [_]
             (let [block (om/get-node owner "highlight")]
               (.highlightBlock js/hljs block)))
  (will-unmount [_])
  (render [_]
          (let [code-opts (if language {:class language} {})]
            (d/div
             {:class "highlight solarized-light-wrapper"}
             (d/pre {:ref "highlight"}
                    (d/code code-opts code))))))

(defcomponentk example
  [[:data body code] state]
  (init-state [_] {:open? false})
  (render-state
   [_ {:keys [open?]}]
   (d/div {:class "playground"}
          (bs-example body)
          (when open?
            (->code-block {:code code}))
          (d/a {:class (d/class-set
                        {:code-toggle true
                         :open open?})
                :on-click #(swap! state update-in [:open?] not)}
               (if open?
                 "hide code"
                 "show code")))))

(defn TODO []
  (->example {:code "TODO" :body (d/p "TODO")}))
