class RmModel extends HTMLElement {

    static observedAttributes = ["name"];

    constructor() {
        super();
        const shadow = this.attachShadow({mode: "open"});
        shadow.innerHTML = `<div style="display: flex; flex-direction: column; gap: 1rem; margin: 3rem 0;"><div id="name"></div><div id="schemas" style="display:flex; flex-direction: column; gap: 1rem;"><slot></slot></div></div>`
    }

    attributeChangedCallback(name, oldValue, newValue) {
        if (name === "name") {
            this.shadowRoot.getElementById("name").innerHTML = `${newValue}:`
        }
    }
}

class RmSchema extends HTMLElement {

    static observedAttributes = ["name"];

    constructor() {
        super();
        const shadow = this.attachShadow({mode: "open"});
        shadow.innerHTML = `<div style="display: flex; gap: 1rem;"><span id="name"></span>(<span id="attributes" style="display: inline-flex; gap: 1rem;"><slot></slot></span>)</div>`
    }

    attributeChangedCallback(name, oldValue, newValue) {
        if (name === "name") {
            this.shadowRoot.getElementById("name").innerHTML = newValue
        }
    }
}

class RmAttribute extends HTMLElement {

    constructor() {
        super();
        const shadow = this.attachShadow({mode: "open"});
        shadow.innerHTML = `<span><slot></slot></span>`
    }
}

class RmPrimaryKey extends HTMLElement {

    constructor() {
        super();
        const shadow = this.attachShadow({mode: "open"});
        shadow.innerHTML = `<span style="display: inline-flex; gap: 1rem; border-bottom: 1px solid black;"><slot></slot></span>`
    }
}

class RmForeignKey extends HTMLElement {

    constructor() {
        super();
        const shadow = this.attachShadow({mode: "open"});
        shadow.innerHTML = `<span style="display: inline-flex; gap: 1rem; border-top: 1px solid black;"><slot></slot></span>`
    }
}

customElements.define("rm-model", RmModel);
customElements.define("rm-schema", RmSchema);
customElements.define("rm-attribute", RmAttribute);
customElements.define("rm-primary-key", RmPrimaryKey);
customElements.define("rm-foreign-key", RmForeignKey);
