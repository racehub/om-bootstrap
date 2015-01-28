(ns om-bootstrap.modal
  "IN PROGRESS work on a modal component. Depends on a fade mixin."
  (:require [om.core :as om]
            [om-bootstrap.mixins :refer [fade-mixin]]
            [om-bootstrap.types :as t]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s]
            [om-bootstrap.util :as u])
  (:require-macros [schema.macros :as sm]))

;; ## Schema
;;
;; TODO: Should we make on-request-hide a channel that the user
;; optionally supplies?
;;
;; TODO: Figure out how the hell the d/div, etc are defined. I don't
;; like this pattern of having to explicitly include a "children"
;; field. We should always take the properties as the first argument,
;; and then optionally take any number of children after that.... but
;; does that work with om/build? I don't think so. This is one to ask
;; David Nolen about.

(def Modal
  "Options for the modal."
  {:header s/Any
   :footer s/Any
   (s/optional-key :backdrop?) s/Bool
   (s/optional-key :keyboard?) s/Bool
   (s/optional-key :close-button?) s/Bool
   (s/optional-key :animate?) s/Bool})

(sm/defn render-header :- t/Component
  "Renders the header for the modal."
  [{:keys [close-button? on-request-hide title]} :- Modal]
  (let [close-button (when close-button?
                       (d/button {:type "button"
                                  :class "close"
                                  :aria-hidden true
                                  :on-click on-request-hide}
                                 "&times;"))]
    (d/div {:class "modal-header"}
           close-button
           (if (.isValidComponent js/React title)
             title
             (d/h4 {:class "modal-title"} title)))))

(sm/defn render-backdrop :- t/Component
  "Renders the backdrop behind the modal."
  [{:keys [animation? backdrop? on-request-hide] :- Modal} modal-elem]
  (let [klasses {:modal-backdrop true
                 :fade animation?
                 :in (or (not animation?)
                         (not (.querySelectorAll js/document)))}]
    (d/div {:class (d/class-set klasses)
            :on-click (when backdrop?
                        (fn [e]
                          (when (= (.-target e)
                                   (.-currentTarget e))
                            (on-request-hide))))
            :ref "backdrop"}
           modal-elem)))

(defcomponentk modal*
  "Component that renders a Modal. Manages it's own toggle state"
  [owner state]
  (:mixins fade-mixin)
  (render [_]
          (let [{:keys [opts children]} (om/get-props owner)
                [bs props] (t/separate Modal opts {:bs-class "modal"})
                show! (aget owner "show")
                hide! (aget owner "hide")
                visible? (aget owner "isVisible")
                classes {:modal true
                         :fade true
                         :in (visible? owner)}]
            (d/div (u/merge-props props
                                  {:class (t/bs-class-set bs)}
                                  {:class (t/class-map classes)})
                   (d/div {:class "modal-dialog"}
                          (d/div {:class "modal-content"}
                                 (d/div {:class "modal-header"}
                                        (when (:close-button? bs)
                                          (d/button {:type         "button"
                                                     :class        "close"
                                                     :aria-hidden  true
                                                     :data-dismiss "modal"}
                                                    "&times;"))
                                        (:header bs))
                                 (d/div {:class "modal-body"}
                                        children)
                                 (d/div {:class "modal-footer"}
                                        (:footer bs))))))))
(sm/defn modal
  [opts :- Modal & children]
  (->modal* {:opts opts :children children}))

;; TODO: Look up the bootstrap modal mixin here!
;;
;; https://gist.github.com/insin/8449696
#_
(defcomponentk modal
  "Component to render a Bootstrap modal to the DOM."
  [[:data animation? on-request-hide {keyboard? true} :as modal] :- Modal
   owner state]
  (:mixins fade-mixin set-listener-mixin)
  (init-state [_] {:keyup (chan)})
  (will-mount [_]
              (let [c (:keyup @state)]
                (go-loop []
                  (let [item (a/<! c)]
                    (if (and keyboard? (.-keycode item))
                      (on-request-hide)))
                  (recur))))
  (did-mount [_] (.set-listener owner "keyup" (:keyup @state)))
  (render [_]
          (d/div {} (d/p {} "Placeholder."))
          #_(let [style {:display "block"}
                  klasses {:modal true
                           :fade animation?
                           :in (or (not animation?)
                                   (not (.querySelectorAll js/document)))}
                  modal-elem (d/div {:title nil
                                     :tab-index "-1"
                                     :role "dialog"
                                     :class (d/class-set klasses)
                                     :ref "modal"}
                                    (d/div {:class "modal-dialog"}
                                           (d/div {:class "modal-content"}
                                                  (when (:title modal)
                                                    (render-header modal))
                                                  (:children modal))))]
              (if (:backdrop? modal)
                (render-backdrop modal modal-elem)
                modal-elem))))
