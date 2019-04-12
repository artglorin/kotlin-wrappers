@file:JsModule("antd")

package kantd.general.button

import react.Component
import react.RProps
import react.RState

external interface BaseButtonProps : RProps {
    var type: String?
    var icon: String?
    var shape: String?
    var size: String?
    var loading: dynamic // boolean | { delay: number };
    var prefixCls: String?
    var className: String?
    var ghost: Boolean?
    var block: Boolean?
    var onClick: dynamic
}

external interface AnchorButtonProps : BaseButtonProps {
    var href: String?
    var target: String?
    var download: Any?
    var hrefLang: String?
    var media: String?
    var rel: String?
    var referrerPolicy: String?
}

external interface NativeButtonProps : BaseButtonProps {
    var href: String?
    var target: String?
}

external interface ButtonState : RState {
    var loading: dynamic //boolean | { delay?: number };
    var hasTwoCNChar: Boolean
}

internal external class Button : Component<BaseButtonProps, ButtonState> {
    override fun render(): dynamic = definedExternally
}
