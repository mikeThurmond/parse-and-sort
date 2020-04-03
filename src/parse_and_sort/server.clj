(ns parse-and-sort.server
  (:require
    [parse-and-sort.routes.records :as records]
    [ring.middleware.params :as params]
    [reitit.ring.middleware.muuntaja :as muuntaja]
    [reitit.coercion.spec]
    [reitit.ring.coercion :as coercion]
    [reitit.ring :as ring]
    [muuntaja.core :as m]))

(def app
  (ring/ring-handler
    (ring/router
      [records/routes]
      {:data {:coercion reitit.coercion.spec/coercion
              :muuntaja m/instance
              :middleware [params/wrap-params
                           muuntaja/format-middleware
                           coercion/coerce-exceptions-middleware
                           coercion/coerce-request-middleware
                           coercion/coerce-response-middleware]}})
    (ring/create-default-handler)))