package kantd.general.button

import kotlinext.js.js
import kotlinext.js.objectAssign
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RProps
import react.ReactElement
import kotlin.reflect.KClass

enum class AntdButtonType(val buttonType: String) {
    DEFAULT("default"),
    PRIMARY("primary"),
    GHOST("ghost"),
    DASHED("dashed"),
    DANGER("danger")
}

enum class AntdButtonShape(val buttonShape: String) {
    CIRCLE("circle"),
    CIRCLE_OUTLINE("circle-outline"),
    ROUND("round")
}

enum class AntdButtonSize(val buttonSize: String) {
    LARGE("large"),
    DEFAULT("default"),
    SMALL("small")
}

enum class AntdButtonHTMLTypes(val buttonHtmlType: String) {
    SUBMIT("submit"),
    BUTTON("button"),
    RESET("reset")
}

interface AntdBaseButtonProps : RProps {
    var type: AntdButtonType?
    var icon: String?
    var shape: AntdButtonShape?
    var size: AntdButtonSize?
    var loading: Boolean?
    var delay: Number?
    var block: Boolean?
    var buttonText: String?
    var content: (RBuilder.() -> Unit)?
    var cleanProps: List<String>?
    var onClick: ((Event) -> Unit)?
}

interface AntdAnchorButtonProps : AntdBaseButtonProps {
    var href: String?
    var target: String?
    var download: Any?
    var hrefLang: String?
    var media: String?
    var rel: String?
    var referrerPolicy: String?
}

interface AntdNativeButtonProps : AntdBaseButtonProps {
    var href: String?
    var target: String?
    var autoFocus: Boolean?
    var disabled: Boolean?
    var form: String?
    var formAction: String?
    var formEncType: String?
    var formMethod: String?
    var formNoValidate: Boolean?
    var formTarget: String?
    var name: String?
    var value: dynamic // string | string[] | number
}

infix fun <T : RProps> T.deleteProps(propsName: String) {
    delete(this.asDynamic()[propsName])
}

open class AntdButton<T : AntdBaseButtonProps>(props: T) : RComponent<T, ButtonState>(props) {
    override fun RBuilder.render() {
        child(Button::class) {
            val builder = this
            objectAssign(attrs, props)
            attrs {
                type = props.type?.buttonType
                shape = props.shape?.buttonShape
                size = props.size?.buttonSize
                props.delay
                    .also { delay ->
                        if (delay == null) {
                            loading = props.loading
                        } else {
                            loading = js {
                                this.delay = delay
                            }
                        }
                    }
                ghost = props.type == AntdButtonType.GHOST
                props.cleanProps?.forEach { attrs deleteProps it }
                attrs deleteProps "content"
                attrs deleteProps "cleanProps"
                attrs deleteProps "buttonText"
            }
            props.buttonText?.let {
                +it
            }
            props.content?.invoke(builder)
        }
    }
}

external fun delete(property: dynamic): Unit = definedExternally

fun RBuilder.antdBaseButton(params: (AntdBaseButtonProps.() -> Unit)? = null): ReactElement {
    @Suppress("UNCHECKED_CAST")
    return child(AntdButton::class as KClass<AntdButton<AntdBaseButtonProps>>) {
        params?.invoke(attrs)
    }
}

fun RBuilder.antdAnchorButton(params: (AntdAnchorButtonProps.() -> Unit)? = null): ReactElement {
    @Suppress("UNCHECKED_CAST")
    return child(AntdButton::class as KClass<AntdButton<AntdAnchorButtonProps>>) {
        params?.invoke(attrs)
    }
}

fun RBuilder.antdNativeButton(params: (AntdNativeButtonProps.() -> Unit)? = null): ReactElement {
    @Suppress("UNCHECKED_CAST")
    return child(AntdButton::class as KClass<AntdButton<AntdNativeButtonProps>>) {
        params?.invoke(attrs)
    }
}