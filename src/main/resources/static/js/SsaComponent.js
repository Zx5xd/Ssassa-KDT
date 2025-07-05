(function () {
  const e = document.createElement("link").relList;
  if (e && e.supports && e.supports("modulepreload")) return;
  for (const s of document.querySelectorAll('link[rel="modulepreload"]')) r(s);
  new MutationObserver((s) => {
    for (const o of s)
      if (o.type === "childList")
        for (const n of o.addedNodes)
          n.tagName === "LINK" && n.rel === "modulepreload" && r(n);
  }).observe(document, { childList: !0, subtree: !0 });
  function i(s) {
    const o = {};
    return (
      s.integrity && (o.integrity = s.integrity),
      s.referrerPolicy && (o.referrerPolicy = s.referrerPolicy),
      s.crossOrigin === "use-credentials"
        ? (o.credentials = "include")
        : s.crossOrigin === "anonymous"
        ? (o.credentials = "omit")
        : (o.credentials = "same-origin"),
      o
    );
  }
  function r(s) {
    if (s.ep) return;
    s.ep = !0;
    const o = i(s);
    fetch(s.href, o);
  }
})();
/**
 * @license
 * Copyright 2019 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ const ae = globalThis,
  ye =
    ae.ShadowRoot &&
    (ae.ShadyCSS === void 0 || ae.ShadyCSS.nativeShadow) &&
    "adoptedStyleSheets" in Document.prototype &&
    "replace" in CSSStyleSheet.prototype,
  $e = Symbol(),
  ke = new WeakMap();
let Ne = class {
  constructor(e, i, r) {
    if (((this._$cssResult$ = !0), r !== $e))
      throw Error(
        "CSSResult is not constructable. Use `unsafeCSS` or `css` instead."
      );
    (this.cssText = e), (this.t = i);
  }
  get styleSheet() {
    let e = this.o;
    const i = this.t;
    if (ye && e === void 0) {
      const r = i !== void 0 && i.length === 1;
      r && (e = ke.get(i)),
        e === void 0 &&
          ((this.o = e = new CSSStyleSheet()).replaceSync(this.cssText),
          r && ke.set(i, e));
    }
    return e;
  }
  toString() {
    return this.cssText;
  }
};
const Ye = (t) => new Ne(typeof t == "string" ? t : t + "", void 0, $e),
  U = (t, ...e) => {
    const i =
      t.length === 1
        ? t[0]
        : e.reduce(
            (r, s, o) =>
              r +
              ((n) => {
                if (n._$cssResult$ === !0) return n.cssText;
                if (typeof n == "number") return n;
                throw Error(
                  "Value passed to 'css' function must be a 'css' function result: " +
                    n +
                    ". Use 'unsafeCSS' to pass non-literal values, but take care to ensure page security."
                );
              })(s) +
              t[o + 1],
            t[0]
          );
    return new Ne(i, t, $e);
  },
  Ge = (t, e) => {
    if (ye)
      t.adoptedStyleSheets = e.map((i) =>
        i instanceof CSSStyleSheet ? i : i.styleSheet
      );
    else
      for (const i of e) {
        const r = document.createElement("style"),
          s = ae.litNonce;
        s !== void 0 && r.setAttribute("nonce", s),
          (r.textContent = i.cssText),
          t.appendChild(r);
      }
  },
  ze = ye
    ? (t) => t
    : (t) =>
        t instanceof CSSStyleSheet
          ? ((e) => {
              let i = "";
              for (const r of e.cssRules) i += r.cssText;
              return Ye(i);
            })(t)
          : t;
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ const {
    is: Xe,
    defineProperty: et,
    getOwnPropertyDescriptor: tt,
    getOwnPropertyNames: it,
    getOwnPropertySymbols: st,
    getPrototypeOf: rt,
  } = Object,
  R = globalThis,
  Ee = R.trustedTypes,
  ot = Ee ? Ee.emptyScript : "",
  ue = R.reactiveElementPolyfillSupport,
  G = (t, e) => t,
  le = {
    toAttribute(t, e) {
      switch (e) {
        case Boolean:
          t = t ? ot : null;
          break;
        case Object:
        case Array:
          t = t == null ? t : JSON.stringify(t);
      }
      return t;
    },
    fromAttribute(t, e) {
      let i = t;
      switch (e) {
        case Boolean:
          i = t !== null;
          break;
        case Number:
          i = t === null ? null : Number(t);
          break;
        case Object:
        case Array:
          try {
            i = JSON.parse(t);
          } catch {
            i = null;
          }
      }
      return i;
    },
  },
  we = (t, e) => !Xe(t, e),
  Se = {
    attribute: !0,
    type: String,
    converter: le,
    reflect: !1,
    useDefault: !1,
    hasChanged: we,
  };
Symbol.metadata ?? (Symbol.metadata = Symbol("metadata")),
  R.litPropertyMetadata ?? (R.litPropertyMetadata = new WeakMap());
let Z = class extends HTMLElement {
  static addInitializer(e) {
    this._$Ei(), (this.l ?? (this.l = [])).push(e);
  }
  static get observedAttributes() {
    return this.finalize(), this._$Eh && [...this._$Eh.keys()];
  }
  static createProperty(e, i = Se) {
    if (
      (i.state && (i.attribute = !1),
      this._$Ei(),
      this.prototype.hasOwnProperty(e) && ((i = Object.create(i)).wrapped = !0),
      this.elementProperties.set(e, i),
      !i.noAccessor)
    ) {
      const r = Symbol(),
        s = this.getPropertyDescriptor(e, r, i);
      s !== void 0 && et(this.prototype, e, s);
    }
  }
  static getPropertyDescriptor(e, i, r) {
    const { get: s, set: o } = tt(this.prototype, e) ?? {
      get() {
        return this[i];
      },
      set(n) {
        this[i] = n;
      },
    };
    return {
      get: s,
      set(n) {
        const d = s == null ? void 0 : s.call(this);
        o == null || o.call(this, n), this.requestUpdate(e, d, r);
      },
      configurable: !0,
      enumerable: !0,
    };
  }
  static getPropertyOptions(e) {
    return this.elementProperties.get(e) ?? Se;
  }
  static _$Ei() {
    if (this.hasOwnProperty(G("elementProperties"))) return;
    const e = rt(this);
    e.finalize(),
      e.l !== void 0 && (this.l = [...e.l]),
      (this.elementProperties = new Map(e.elementProperties));
  }
  static finalize() {
    if (this.hasOwnProperty(G("finalized"))) return;
    if (
      ((this.finalized = !0), this._$Ei(), this.hasOwnProperty(G("properties")))
    ) {
      const i = this.properties,
        r = [...it(i), ...st(i)];
      for (const s of r) this.createProperty(s, i[s]);
    }
    const e = this[Symbol.metadata];
    if (e !== null) {
      const i = litPropertyMetadata.get(e);
      if (i !== void 0)
        for (const [r, s] of i) this.elementProperties.set(r, s);
    }
    this._$Eh = new Map();
    for (const [i, r] of this.elementProperties) {
      const s = this._$Eu(i, r);
      s !== void 0 && this._$Eh.set(s, i);
    }
    this.elementStyles = this.finalizeStyles(this.styles);
  }
  static finalizeStyles(e) {
    const i = [];
    if (Array.isArray(e)) {
      const r = new Set(e.flat(1 / 0).reverse());
      for (const s of r) i.unshift(ze(s));
    } else e !== void 0 && i.push(ze(e));
    return i;
  }
  static _$Eu(e, i) {
    const r = i.attribute;
    return r === !1
      ? void 0
      : typeof r == "string"
      ? r
      : typeof e == "string"
      ? e.toLowerCase()
      : void 0;
  }
  constructor() {
    super(),
      (this._$Ep = void 0),
      (this.isUpdatePending = !1),
      (this.hasUpdated = !1),
      (this._$Em = null),
      this._$Ev();
  }
  _$Ev() {
    var e;
    (this._$ES = new Promise((i) => (this.enableUpdating = i))),
      (this._$AL = new Map()),
      this._$E_(),
      this.requestUpdate(),
      (e = this.constructor.l) == null || e.forEach((i) => i(this));
  }
  addController(e) {
    var i;
    (this._$EO ?? (this._$EO = new Set())).add(e),
      this.renderRoot !== void 0 &&
        this.isConnected &&
        ((i = e.hostConnected) == null || i.call(e));
  }
  removeController(e) {
    var i;
    (i = this._$EO) == null || i.delete(e);
  }
  _$E_() {
    const e = new Map(),
      i = this.constructor.elementProperties;
    for (const r of i.keys())
      this.hasOwnProperty(r) && (e.set(r, this[r]), delete this[r]);
    e.size > 0 && (this._$Ep = e);
  }
  createRenderRoot() {
    const e =
      this.shadowRoot ?? this.attachShadow(this.constructor.shadowRootOptions);
    return Ge(e, this.constructor.elementStyles), e;
  }
  connectedCallback() {
    var e;
    this.renderRoot ?? (this.renderRoot = this.createRenderRoot()),
      this.enableUpdating(!0),
      (e = this._$EO) == null ||
        e.forEach((i) => {
          var r;
          return (r = i.hostConnected) == null ? void 0 : r.call(i);
        });
  }
  enableUpdating(e) {}
  disconnectedCallback() {
    var e;
    (e = this._$EO) == null ||
      e.forEach((i) => {
        var r;
        return (r = i.hostDisconnected) == null ? void 0 : r.call(i);
      });
  }
  attributeChangedCallback(e, i, r) {
    this._$AK(e, r);
  }
  _$ET(e, i) {
    var o;
    const r = this.constructor.elementProperties.get(e),
      s = this.constructor._$Eu(e, r);
    if (s !== void 0 && r.reflect === !0) {
      const n = (
        ((o = r.converter) == null ? void 0 : o.toAttribute) !== void 0
          ? r.converter
          : le
      ).toAttribute(i, r.type);
      (this._$Em = e),
        n == null ? this.removeAttribute(s) : this.setAttribute(s, n),
        (this._$Em = null);
    }
  }
  _$AK(e, i) {
    var o, n;
    const r = this.constructor,
      s = r._$Eh.get(e);
    if (s !== void 0 && this._$Em !== s) {
      const d = r.getPropertyOptions(s),
        a =
          typeof d.converter == "function"
            ? { fromAttribute: d.converter }
            : ((o = d.converter) == null ? void 0 : o.fromAttribute) !== void 0
            ? d.converter
            : le;
      (this._$Em = s),
        (this[s] =
          a.fromAttribute(i, d.type) ??
          ((n = this._$Ej) == null ? void 0 : n.get(s)) ??
          null),
        (this._$Em = null);
    }
  }
  requestUpdate(e, i, r) {
    var s;
    if (e !== void 0) {
      const o = this.constructor,
        n = this[e];
      if (
        (r ?? (r = o.getPropertyOptions(e)),
        !(
          (r.hasChanged ?? we)(n, i) ||
          (r.useDefault &&
            r.reflect &&
            n === ((s = this._$Ej) == null ? void 0 : s.get(e)) &&
            !this.hasAttribute(o._$Eu(e, r)))
        ))
      )
        return;
      this.C(e, i, r);
    }
    this.isUpdatePending === !1 && (this._$ES = this._$EP());
  }
  C(e, i, { useDefault: r, reflect: s, wrapped: o }, n) {
    (r &&
      !(this._$Ej ?? (this._$Ej = new Map())).has(e) &&
      (this._$Ej.set(e, n ?? i ?? this[e]), o !== !0 || n !== void 0)) ||
      (this._$AL.has(e) ||
        (this.hasUpdated || r || (i = void 0), this._$AL.set(e, i)),
      s === !0 &&
        this._$Em !== e &&
        (this._$Eq ?? (this._$Eq = new Set())).add(e));
  }
  async _$EP() {
    this.isUpdatePending = !0;
    try {
      await this._$ES;
    } catch (i) {
      Promise.reject(i);
    }
    const e = this.scheduleUpdate();
    return e != null && (await e), !this.isUpdatePending;
  }
  scheduleUpdate() {
    return this.performUpdate();
  }
  performUpdate() {
    var r;
    if (!this.isUpdatePending) return;
    if (!this.hasUpdated) {
      if (
        (this.renderRoot ?? (this.renderRoot = this.createRenderRoot()),
        this._$Ep)
      ) {
        for (const [o, n] of this._$Ep) this[o] = n;
        this._$Ep = void 0;
      }
      const s = this.constructor.elementProperties;
      if (s.size > 0)
        for (const [o, n] of s) {
          const { wrapped: d } = n,
            a = this[o];
          d !== !0 ||
            this._$AL.has(o) ||
            a === void 0 ||
            this.C(o, void 0, n, a);
        }
    }
    let e = !1;
    const i = this._$AL;
    try {
      (e = this.shouldUpdate(i)),
        e
          ? (this.willUpdate(i),
            (r = this._$EO) == null ||
              r.forEach((s) => {
                var o;
                return (o = s.hostUpdate) == null ? void 0 : o.call(s);
              }),
            this.update(i))
          : this._$EM();
    } catch (s) {
      throw ((e = !1), this._$EM(), s);
    }
    e && this._$AE(i);
  }
  willUpdate(e) {}
  _$AE(e) {
    var i;
    (i = this._$EO) == null ||
      i.forEach((r) => {
        var s;
        return (s = r.hostUpdated) == null ? void 0 : s.call(r);
      }),
      this.hasUpdated || ((this.hasUpdated = !0), this.firstUpdated(e)),
      this.updated(e);
  }
  _$EM() {
    (this._$AL = new Map()), (this.isUpdatePending = !1);
  }
  get updateComplete() {
    return this.getUpdateComplete();
  }
  getUpdateComplete() {
    return this._$ES;
  }
  shouldUpdate(e) {
    return !0;
  }
  update(e) {
    this._$Eq && (this._$Eq = this._$Eq.forEach((i) => this._$ET(i, this[i]))),
      this._$EM();
  }
  updated(e) {}
  firstUpdated(e) {}
};
(Z.elementStyles = []),
  (Z.shadowRootOptions = { mode: "open" }),
  (Z[G("elementProperties")] = new Map()),
  (Z[G("finalized")] = new Map()),
  ue == null || ue({ ReactiveElement: Z }),
  (R.reactiveElementVersions ?? (R.reactiveElementVersions = [])).push("2.1.0");
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ const X = globalThis,
  ce = X.trustedTypes,
  Ce = ce ? ce.createPolicy("lit-html", { createHTML: (t) => t }) : void 0,
  Fe = "$lit$",
  O = `lit$${Math.random().toFixed(9).slice(2)}$`,
  Be = "?" + O,
  nt = `<${Be}>`,
  B = document,
  ee = () => B.createComment(""),
  te = (t) => t === null || (typeof t != "object" && typeof t != "function"),
  xe = Array.isArray,
  at = (t) =>
    xe(t) || typeof (t == null ? void 0 : t[Symbol.iterator]) == "function",
  fe = `[ 	
\f\r]`,
  Q = /<(?:(!--|\/[^a-zA-Z])|(\/?[a-zA-Z][^>\s]*)|(\/?$))/g,
  Ie = /-->/g,
  Pe = />/g,
  D = RegExp(
    `>|${fe}(?:([^\\s"'>=/]+)(${fe}*=${fe}*(?:[^ 	
\f\r"'\`<>=]|("|')|))|$)`,
    "g"
  ),
  Oe = /'/g,
  Re = /"/g,
  He = /^(?:script|style|textarea|title)$/i,
  lt =
    (t) =>
    (e, ...i) => ({ _$litType$: t, strings: e, values: i }),
  f = lt(1),
  M = Symbol.for("lit-noChange"),
  z = Symbol.for("lit-nothing"),
  Me = new WeakMap(),
  N = B.createTreeWalker(B, 129);
function Ve(t, e) {
  if (!xe(t) || !t.hasOwnProperty("raw"))
    throw Error("invalid template strings array");
  return Ce !== void 0 ? Ce.createHTML(e) : e;
}
const ct = (t, e) => {
  const i = t.length - 1,
    r = [];
  let s,
    o = e === 2 ? "<svg>" : e === 3 ? "<math>" : "",
    n = Q;
  for (let d = 0; d < i; d++) {
    const a = t[d];
    let c,
      p,
      l = -1,
      h = 0;
    for (; h < a.length && ((n.lastIndex = h), (p = n.exec(a)), p !== null); )
      (h = n.lastIndex),
        n === Q
          ? p[1] === "!--"
            ? (n = Ie)
            : p[1] !== void 0
            ? (n = Pe)
            : p[2] !== void 0
            ? (He.test(p[2]) && (s = RegExp("</" + p[2], "g")), (n = D))
            : p[3] !== void 0 && (n = D)
          : n === D
          ? p[0] === ">"
            ? ((n = s ?? Q), (l = -1))
            : p[1] === void 0
            ? (l = -2)
            : ((l = n.lastIndex - p[2].length),
              (c = p[1]),
              (n = p[3] === void 0 ? D : p[3] === '"' ? Re : Oe))
          : n === Re || n === Oe
          ? (n = D)
          : n === Ie || n === Pe
          ? (n = Q)
          : ((n = D), (s = void 0));
    const m = n === D && t[d + 1].startsWith("/>") ? " " : "";
    o +=
      n === Q
        ? a + nt
        : l >= 0
        ? (r.push(c), a.slice(0, l) + Fe + a.slice(l) + O + m)
        : a + O + (l === -2 ? d : m);
  }
  return [
    Ve(
      t,
      o + (t[i] || "<?>") + (e === 2 ? "</svg>" : e === 3 ? "</math>" : "")
    ),
    r,
  ];
};
class ie {
  constructor({ strings: e, _$litType$: i }, r) {
    let s;
    this.parts = [];
    let o = 0,
      n = 0;
    const d = e.length - 1,
      a = this.parts,
      [c, p] = ct(e, i);
    if (
      ((this.el = ie.createElement(c, r)),
      (N.currentNode = this.el.content),
      i === 2 || i === 3)
    ) {
      const l = this.el.content.firstChild;
      l.replaceWith(...l.childNodes);
    }
    for (; (s = N.nextNode()) !== null && a.length < d; ) {
      if (s.nodeType === 1) {
        if (s.hasAttributes())
          for (const l of s.getAttributeNames())
            if (l.endsWith(Fe)) {
              const h = p[n++],
                m = s.getAttribute(l).split(O),
                b = /([.?@])?(.*)/.exec(h);
              a.push({
                type: 1,
                index: o,
                name: b[2],
                strings: m,
                ctor:
                  b[1] === "."
                    ? pt
                    : b[1] === "?"
                    ? ht
                    : b[1] === "@"
                    ? ut
                    : pe,
              }),
                s.removeAttribute(l);
            } else
              l.startsWith(O) &&
                (a.push({ type: 6, index: o }), s.removeAttribute(l));
        if (He.test(s.tagName)) {
          const l = s.textContent.split(O),
            h = l.length - 1;
          if (h > 0) {
            s.textContent = ce ? ce.emptyScript : "";
            for (let m = 0; m < h; m++)
              s.append(l[m], ee()),
                N.nextNode(),
                a.push({ type: 2, index: ++o });
            s.append(l[h], ee());
          }
        }
      } else if (s.nodeType === 8)
        if (s.data === Be) a.push({ type: 2, index: o });
        else {
          let l = -1;
          for (; (l = s.data.indexOf(O, l + 1)) !== -1; )
            a.push({ type: 7, index: o }), (l += O.length - 1);
        }
      o++;
    }
  }
  static createElement(e, i) {
    const r = B.createElement("template");
    return (r.innerHTML = e), r;
  }
}
function W(t, e, i = t, r) {
  var n, d;
  if (e === M) return e;
  let s = r !== void 0 ? ((n = i._$Co) == null ? void 0 : n[r]) : i._$Cl;
  const o = te(e) ? void 0 : e._$litDirective$;
  return (
    (s == null ? void 0 : s.constructor) !== o &&
      ((d = s == null ? void 0 : s._$AO) == null || d.call(s, !1),
      o === void 0 ? (s = void 0) : ((s = new o(t)), s._$AT(t, i, r)),
      r !== void 0 ? ((i._$Co ?? (i._$Co = []))[r] = s) : (i._$Cl = s)),
    s !== void 0 && (e = W(t, s._$AS(t, e.values), s, r)),
    e
  );
}
let dt = class {
  constructor(e, i) {
    (this._$AV = []), (this._$AN = void 0), (this._$AD = e), (this._$AM = i);
  }
  get parentNode() {
    return this._$AM.parentNode;
  }
  get _$AU() {
    return this._$AM._$AU;
  }
  u(e) {
    const {
        el: { content: i },
        parts: r,
      } = this._$AD,
      s = ((e == null ? void 0 : e.creationScope) ?? B).importNode(i, !0);
    N.currentNode = s;
    let o = N.nextNode(),
      n = 0,
      d = 0,
      a = r[0];
    for (; a !== void 0; ) {
      if (n === a.index) {
        let c;
        a.type === 2
          ? (c = new J(o, o.nextSibling, this, e))
          : a.type === 1
          ? (c = new a.ctor(o, a.name, a.strings, this, e))
          : a.type === 6 && (c = new ft(o, this, e)),
          this._$AV.push(c),
          (a = r[++d]);
      }
      n !== (a == null ? void 0 : a.index) && ((o = N.nextNode()), n++);
    }
    return (N.currentNode = B), s;
  }
  p(e) {
    let i = 0;
    for (const r of this._$AV)
      r !== void 0 &&
        (r.strings !== void 0
          ? (r._$AI(e, r, i), (i += r.strings.length - 2))
          : r._$AI(e[i])),
        i++;
  }
};
class J {
  get _$AU() {
    var e;
    return ((e = this._$AM) == null ? void 0 : e._$AU) ?? this._$Cv;
  }
  constructor(e, i, r, s) {
    (this.type = 2),
      (this._$AH = z),
      (this._$AN = void 0),
      (this._$AA = e),
      (this._$AB = i),
      (this._$AM = r),
      (this.options = s),
      (this._$Cv = (s == null ? void 0 : s.isConnected) ?? !0);
  }
  get parentNode() {
    let e = this._$AA.parentNode;
    const i = this._$AM;
    return (
      i !== void 0 &&
        (e == null ? void 0 : e.nodeType) === 11 &&
        (e = i.parentNode),
      e
    );
  }
  get startNode() {
    return this._$AA;
  }
  get endNode() {
    return this._$AB;
  }
  _$AI(e, i = this) {
    (e = W(this, e, i)),
      te(e)
        ? e === z || e == null || e === ""
          ? (this._$AH !== z && this._$AR(), (this._$AH = z))
          : e !== this._$AH && e !== M && this._(e)
        : e._$litType$ !== void 0
        ? this.$(e)
        : e.nodeType !== void 0
        ? this.T(e)
        : at(e)
        ? this.k(e)
        : this._(e);
  }
  O(e) {
    return this._$AA.parentNode.insertBefore(e, this._$AB);
  }
  T(e) {
    this._$AH !== e && (this._$AR(), (this._$AH = this.O(e)));
  }
  _(e) {
    this._$AH !== z && te(this._$AH)
      ? (this._$AA.nextSibling.data = e)
      : this.T(B.createTextNode(e)),
      (this._$AH = e);
  }
  $(e) {
    var o;
    const { values: i, _$litType$: r } = e,
      s =
        typeof r == "number"
          ? this._$AC(e)
          : (r.el === void 0 &&
              (r.el = ie.createElement(Ve(r.h, r.h[0]), this.options)),
            r);
    if (((o = this._$AH) == null ? void 0 : o._$AD) === s) this._$AH.p(i);
    else {
      const n = new dt(s, this),
        d = n.u(this.options);
      n.p(i), this.T(d), (this._$AH = n);
    }
  }
  _$AC(e) {
    let i = Me.get(e.strings);
    return i === void 0 && Me.set(e.strings, (i = new ie(e))), i;
  }
  k(e) {
    xe(this._$AH) || ((this._$AH = []), this._$AR());
    const i = this._$AH;
    let r,
      s = 0;
    for (const o of e)
      s === i.length
        ? i.push((r = new J(this.O(ee()), this.O(ee()), this, this.options)))
        : (r = i[s]),
        r._$AI(o),
        s++;
    s < i.length && (this._$AR(r && r._$AB.nextSibling, s), (i.length = s));
  }
  _$AR(e = this._$AA.nextSibling, i) {
    var r;
    for (
      (r = this._$AP) == null ? void 0 : r.call(this, !1, !0, i);
      e && e !== this._$AB;

    ) {
      const s = e.nextSibling;
      e.remove(), (e = s);
    }
  }
  setConnected(e) {
    var i;
    this._$AM === void 0 &&
      ((this._$Cv = e), (i = this._$AP) == null || i.call(this, e));
  }
}
class pe {
  get tagName() {
    return this.element.tagName;
  }
  get _$AU() {
    return this._$AM._$AU;
  }
  constructor(e, i, r, s, o) {
    (this.type = 1),
      (this._$AH = z),
      (this._$AN = void 0),
      (this.element = e),
      (this.name = i),
      (this._$AM = s),
      (this.options = o),
      r.length > 2 || r[0] !== "" || r[1] !== ""
        ? ((this._$AH = Array(r.length - 1).fill(new String())),
          (this.strings = r))
        : (this._$AH = z);
  }
  _$AI(e, i = this, r, s) {
    const o = this.strings;
    let n = !1;
    if (o === void 0)
      (e = W(this, e, i, 0)),
        (n = !te(e) || (e !== this._$AH && e !== M)),
        n && (this._$AH = e);
    else {
      const d = e;
      let a, c;
      for (e = o[0], a = 0; a < o.length - 1; a++)
        (c = W(this, d[r + a], i, a)),
          c === M && (c = this._$AH[a]),
          n || (n = !te(c) || c !== this._$AH[a]),
          c === z ? (e = z) : e !== z && (e += (c ?? "") + o[a + 1]),
          (this._$AH[a] = c);
    }
    n && !s && this.j(e);
  }
  j(e) {
    e === z
      ? this.element.removeAttribute(this.name)
      : this.element.setAttribute(this.name, e ?? "");
  }
}
class pt extends pe {
  constructor() {
    super(...arguments), (this.type = 3);
  }
  j(e) {
    this.element[this.name] = e === z ? void 0 : e;
  }
}
class ht extends pe {
  constructor() {
    super(...arguments), (this.type = 4);
  }
  j(e) {
    this.element.toggleAttribute(this.name, !!e && e !== z);
  }
}
class ut extends pe {
  constructor(e, i, r, s, o) {
    super(e, i, r, s, o), (this.type = 5);
  }
  _$AI(e, i = this) {
    if ((e = W(this, e, i, 0) ?? z) === M) return;
    const r = this._$AH,
      s =
        (e === z && r !== z) ||
        e.capture !== r.capture ||
        e.once !== r.once ||
        e.passive !== r.passive,
      o = e !== z && (r === z || s);
    s && this.element.removeEventListener(this.name, this, r),
      o && this.element.addEventListener(this.name, this, e),
      (this._$AH = e);
  }
  handleEvent(e) {
    var i;
    typeof this._$AH == "function"
      ? this._$AH.call(
          ((i = this.options) == null ? void 0 : i.host) ?? this.element,
          e
        )
      : this._$AH.handleEvent(e);
  }
}
class ft {
  constructor(e, i, r) {
    (this.element = e),
      (this.type = 6),
      (this._$AN = void 0),
      (this._$AM = i),
      (this.options = r);
  }
  get _$AU() {
    return this._$AM._$AU;
  }
  _$AI(e) {
    W(this, e);
  }
}
const mt = { I: J },
  me = X.litHtmlPolyfillSupport;
me == null || me(ie, J),
  (X.litHtmlVersions ?? (X.litHtmlVersions = [])).push("3.3.0");
const vt = (t, e, i) => {
  const r = (i == null ? void 0 : i.renderBefore) ?? e;
  let s = r._$litPart$;
  if (s === void 0) {
    const o = (i == null ? void 0 : i.renderBefore) ?? null;
    r._$litPart$ = s = new J(e.insertBefore(ee(), o), o, void 0, i ?? {});
  }
  return s._$AI(t), s;
};
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ const F = globalThis;
let C = class extends Z {
  constructor() {
    super(...arguments),
      (this.renderOptions = { host: this }),
      (this._$Do = void 0);
  }
  createRenderRoot() {
    var i;
    const e = super.createRenderRoot();
    return (
      (i = this.renderOptions).renderBefore ?? (i.renderBefore = e.firstChild),
      e
    );
  }
  update(e) {
    const i = this.render();
    this.hasUpdated || (this.renderOptions.isConnected = this.isConnected),
      super.update(e),
      (this._$Do = vt(i, this.renderRoot, this.renderOptions));
  }
  connectedCallback() {
    var e;
    super.connectedCallback(), (e = this._$Do) == null || e.setConnected(!0);
  }
  disconnectedCallback() {
    var e;
    super.disconnectedCallback(), (e = this._$Do) == null || e.setConnected(!1);
  }
  render() {
    return M;
  }
};
var qe;
(C._$litElement$ = !0),
  (C.finalized = !0),
  (qe = F.litElementHydrateSupport) == null || qe.call(F, { LitElement: C });
const ve = F.litElementPolyfillSupport;
ve == null || ve({ LitElement: C });
(F.litElementVersions ?? (F.litElementVersions = [])).push("4.2.0");
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ const j = (t) => (e, i) => {
  i !== void 0
    ? i.addInitializer(() => {
        customElements.define(t, e);
      })
    : customElements.define(t, e);
};
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ const gt = {
    attribute: !0,
    type: String,
    converter: le,
    reflect: !1,
    hasChanged: we,
  },
  bt = (t = gt, e, i) => {
    const { kind: r, metadata: s } = i;
    let o = globalThis.litPropertyMetadata.get(s);
    if (
      (o === void 0 && globalThis.litPropertyMetadata.set(s, (o = new Map())),
      r === "setter" && ((t = Object.create(t)).wrapped = !0),
      o.set(i.name, t),
      r === "accessor")
    ) {
      const { name: n } = i;
      return {
        set(d) {
          const a = e.get.call(this);
          e.set.call(this, d), this.requestUpdate(n, a, t);
        },
        init(d) {
          return d !== void 0 && this.C(n, void 0, t, d), d;
        },
      };
    }
    if (r === "setter") {
      const { name: n } = i;
      return function (d) {
        const a = this[n];
        e.call(this, d), this.requestUpdate(n, a, t);
      };
    }
    throw Error("Unsupported decorator location: " + r);
  };
function x(t) {
  return (e, i) =>
    typeof i == "object"
      ? bt(t, e, i)
      : ((r, s, o) => {
          const n = s.hasOwnProperty(o);
          return (
            s.constructor.createProperty(o, r),
            n ? Object.getOwnPropertyDescriptor(s, o) : void 0
          );
        })(t, e, i);
}
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ function T(t) {
  return x({ ...t, state: !0, attribute: !1 });
}
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ const yt = (t, e, i) => (
  (i.configurable = !0),
  (i.enumerable = !0),
  Reflect.decorate && typeof e != "object" && Object.defineProperty(t, e, i),
  i
);
/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ function $t(t) {
  return (e, i) => {
    const { slot: r, selector: s } = {},
      o = "slot" + (r ? `[name=${r}]` : ":not([name])");
    return yt(e, i, {
      get() {
        var a;
        const n = (a = this.renderRoot) == null ? void 0 : a.querySelector(o),
          d = (n == null ? void 0 : n.assignedElements(t)) ?? [];
        return s === void 0 ? d : d.filter((c) => c.matches(s));
      },
    });
  };
}
var wt = Object.defineProperty,
  xt = Object.getOwnPropertyDescriptor,
  Ze = (t, e, i, r) => {
    for (
      var s = r > 1 ? void 0 : r ? xt(e, i) : e, o = t.length - 1, n;
      o >= 0;
      o--
    )
      (n = t[o]) && (s = (r ? n(e, i, s) : n(s)) || s);
    return r && s && wt(e, i, s), s;
  };
let de = class extends C {
  updated() {
    if (
      (this.items.forEach((t) => {
        t.classList.remove("first", "last"),
          t.style.removeProperty("--radius"),
          t.style.removeProperty("--radius-hover");
      }),
      this.items.length > 0)
    ) {
      const t = this.items[0];
      t &&
        (t.classList.add("first"),
        t.style.setProperty("--radius", "var(--radius-2) var(--radius-2) 0 0"),
        t.style.setProperty(
          "--radius-hover",
          "var(--radius-2) var(--radius-2) var(--radius-2) 0"
        ));
    }
    if (this.items.length > 1) {
      const t = this.items[this.items.length - 1];
      t &&
        (t.classList.add("last"),
        t.style.setProperty("--radius", "0 0 var(--radius-2) var(--radius-2)"),
        t.style.setProperty(
          "--radius-hover",
          "0 var(--radius-2) var(--radius-2) var(--radius-2)"
        ));
    }
  }
  render() {
    return f`<slot></slot>`;
  }
};
de.styles = U`
    :host {
      height: fit-content;
      display: flex;
      flex-direction: column;
      align-items: center;
    }
  `;
Ze([$t()], de.prototype, "items", 2);
de = Ze([j("category-elem")], de);
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ const We = { ATTRIBUTE: 1, CHILD: 2 },
  Ke =
    (t) =>
    (...e) => ({ _$litDirective$: t, values: e });
let Je = class {
  constructor(e) {}
  get _$AU() {
    return this._$AM._$AU;
  }
  _$AT(e, i, r) {
    (this._$Ct = e), (this._$AM = i), (this._$Ci = r);
  }
  _$AS(e, i) {
    return this.update(e, i);
  }
  update(e, i) {
    return this.render(...i);
  }
};
/**
 * @license
 * Copyright 2018 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ const Qe = "important",
  _t = " !" + Qe,
  At = Ke(
    class extends Je {
      constructor(t) {
        var e;
        if (
          (super(t),
          t.type !== We.ATTRIBUTE ||
            t.name !== "style" ||
            ((e = t.strings) == null ? void 0 : e.length) > 2)
        )
          throw Error(
            "The `styleMap` directive must be used in the `style` attribute and must be the only part in the attribute."
          );
      }
      render(t) {
        return Object.keys(t).reduce((e, i) => {
          const r = t[i];
          return r == null
            ? e
            : e +
                `${(i = i.includes("-")
                  ? i
                  : i
                      .replace(/(?:^(webkit|moz|ms|o)|)(?=[A-Z])/g, "-$&")
                      .toLowerCase())}:${r};`;
        }, "");
      }
      update(t, [e]) {
        const { style: i } = t.element;
        if (this.ft === void 0)
          return (this.ft = new Set(Object.keys(e))), this.render(e);
        for (const r of this.ft)
          e[r] == null &&
            (this.ft.delete(r),
            r.includes("-") ? i.removeProperty(r) : (i[r] = null));
        for (const r in e) {
          const s = e[r];
          if (s != null) {
            this.ft.add(r);
            const o = typeof s == "string" && s.endsWith(_t);
            r.includes("-") || o
              ? i.setProperty(r, o ? s.slice(0, -11) : s, o ? Qe : "")
              : (i[r] = s);
          }
        }
        return M;
      }
    }
  );
var kt = Object.defineProperty,
  zt = Object.getOwnPropertyDescriptor,
  oe = (t, e, i, r) => {
    for (
      var s = r > 1 ? void 0 : r ? zt(e, i) : e, o = t.length - 1, n;
      o >= 0;
      o--
    )
      (n = t[o]) && (s = (r ? n(e, i, s) : n(s)) || s);
    return r && s && kt(e, i, s), s;
  };
let H = class extends C {
  constructor() {
    super(...arguments),
      (this.href = "#"),
      (this.value = ""),
      (this.bgColor = "white"),
      (this.textColor = "black"),
      (this.dataCid = "");
  }
  render() {
    const t = {
        first: this.classList.contains("first"),
        last: this.classList.contains("last"),
      },
      e = Object.entries(t)
        .filter(([, i]) => i)
        .map(([i]) => i)
        .join(" ");
    return f`
      <a
        part="item"
        href="${this.href}"
        class="${e}"
        data-cid="${this.dataCid}"
        style=${At({
          "--hover-bg-color": this.bgColor,
          "--hover-color": this.textColor,
        })}
      >
        <span class="icon"><slot></slot></span>
        <span class="label">${this.value}</span>
      </a>
    `;
  }
};
H.styles = U`
    :host {
      display: block;
      position: relative;
      width: auto;
      height: 40px;
      overflow: visible;
    }

    a {
      position: absolute;
      top: 0;
      left: 0;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      height: 40px;
      width: 40px;
      padding: 0;
      background: var(--gray-0, white);
      text-decoration: none;
      color: var(--item-color, black);
      border-radius: 0;
      font-weight: var(--font-weight-7);
      transition:
              width 0.3s ease,
              background 0.3s ease,
              color 0.3s ease,
              border-radius 0.3s ease;
      z-index: 1;
      box-sizing: border-box;
      overflow: hidden;
    }

    a:hover {
      width: 160px;
      background: var(--hover-bg-color);
      color: var(--hover-color);
      border-radius: 0 var(--radius-2) var(--radius-2) 0;
    }

    a.first {
      border-radius: var(--radius-2) var(--radius-2) 0 0;
    }

    a.last {
      border-radius: 0 0 var(--radius-2) var(--radius-2);
    }

    a.first:hover {
      border-radius: var(--radius-2) var(--radius-2) var(--radius-2) 0;
    }

    a.last:hover {
      border-radius: 0 var(--radius-2) var(--radius-2) var(--radius-2);
    }

    .icon {
      width: 40px;
      height: 40px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 24px;
      flex-shrink: 0;
    }

    .label {
      opacity: 0;
      padding-left: 8px;
      padding-right: 12px;
      font-size: 14px;
      white-space: nowrap;
      transition: opacity 0.2s ease;
    }

    a:hover .label {
      opacity: 1;
    }
  `;
oe([x({ type: String })], H.prototype, "href", 2);
oe([x({ type: String })], H.prototype, "value", 2);
oe([x({ type: String })], H.prototype, "bgColor", 2);
oe([x({ type: String })], H.prototype, "textColor", 2);
oe([x({ type: String })], H.prototype, "dataCid", 2);
H = oe([j("category-item")], H);
var Et = Object.defineProperty,
  St = Object.getOwnPropertyDescriptor,
  I = (t, e, i, r) => {
    for (
      var s = r > 1 ? void 0 : r ? St(e, i) : e, o = t.length - 1, n;
      o >= 0;
      o--
    )
      (n = t[o]) && (s = (r ? n(e, i, s) : n(s)) || s);
    return r && s && Et(e, i, s), s;
  };
let E = class extends C {
  constructor() {
    super(...arguments),
      (this.pId = 0),
      (this.cId = 0),
      (this.ccId = 0),
      (this.pName = ""),
      (this.src = ""),
      (this.desc = ""),
      (this.pReg = ""),
      (this.defaultVariant = 0),
      (this.variants = null),
      (this.checkedIds = new Set());
  }
  get getImageUrl() {
    return `/pdr/img/${this.src}`;
  }
  get getDate() {
    const t = new Date(this.pReg),
      e = t.getFullYear(),
      i = String(t.getMonth() + 1).padStart(2, "0");
    return `${e}.${i}`;
  }
  get getDefaultURI() {
    return this.variants && this.defaultVariant !== 0
      ? `/pd/detail/${this.pId}_${this.defaultVariant}`
      : `/pd/detail/${this.pId}`;
  }
  render() {
    var t;
    return f`
            <div class="p-item">
                <a href="${
                  this.getDefaultURI
                }" class="p-img-container" target="_blank">
                    <img src=${this.getImageUrl}
                         onerror="this.onerror=null; this.src='https://placehold.co/200';"
                         alt="${this.pId}" class="p-img" loading="lazy">
                </a>
                <div class="p-content">
                    <a class="p-name" href="${
                      this.getDefaultURI
                    }" target="_blank">${this.pName}</a>
                    <div class="p-desc">${this.desc}</div>
                    <div class="p-reg">등록일 ${this.getDate}</div>
                </div>
                <div class="p-prices">
                    ${
                      (t = this.variants) == null
                        ? void 0
                        : t.map((e) => this.priceRender(e))
                    }
                </div>
            </div>
        `;
  }
  priceRender(t) {
    const e = this.checkedIds.has(t.id),
      i = f`
            <svg xmlns="http://www.w3.org/2000/svg" enable-background="new 0 0 24 24" viewBox="0 -960 960 960" fill="currentColor">
                <path d="M440-280h80v-160h160v-80H520v-160h-80v160H280v80h160v160ZM200-120q-33 0-56.5-23.5T120-200v-560q0-33 23.5-56.5T200-840h560q33 0 56.5 23.5T840-760v560q0 33-23.5 56.5T760-120H200Z"/>
            </svg>
        `,
      r = f`
            <svg xmlns="http://www.w3.org/2000/svg" enable-background="new 0 0 24 24" viewBox="0 0 24 24" fill="currentColor">
                <rect fill="none" height="24" width="24"/>
                <path d="M3,5v14c0,1.1,0.9,2,2,2h14c1.1,0,2-0.9,2-2V5c0-1.1-0.9-2-2-2H5C3.9,3,3,3.9,3,5z M16.3,16.29L16.3,16.29 c-0.39,0.39-1.02,0.39-1.41,0L12,13.41l-2.89,2.89c-0.39,0.39-1.02,0.39-1.41,0l0,0c-0.39-0.39-0.39-1.02,0-1.41L10.59,12L7.7,9.11 c-0.39-0.39-0.39-1.02,0-1.41l0,0c0.39-0.39,1.02-0.39,1.41,0L12,10.59l2.89-2.88c0.39-0.39,1.02-0.39,1.41,0l0,0 c0.39,0.39,0.39,1.02,0,1.41L13.41,12l2.89,2.88C16.68,15.27,16.68,15.91,16.3,16.29z"/>
            </svg>
        `;
    return t.schedule
      ? f`
            <div class="price-item">
                <a class="price-name" href="/pd/detail/${this.pId}_${t.id}" target="_blank">${t.name}</a>
                <a class="price-value" href="/pd/detail/${this.pId}_${t.id}" target="_blank">가격비교예정</a>
                <span class="material-icons price-plus disabled">${i}</span>
            </div>
        `
      : f`
            <div class="price-item">
                <a class="price-name" href="/pd/detail/${this.pId}_${
          t.id
        }" target="_blank">${t.name}</a>
                <a class="price-value" href="/pd/detail/${this.pId}_${
          t.id
        }" target="_blank">${t.price.toLocaleString()}원</a>
                <span class="material-icons price-plus ${
                  e && "checked"
                }" @click=${() => this.togglePriceCheck(t)}>
                    ${e ? r : i}
                </span>
            </div>
        `;
  }
  dispatchSelect(t) {
    const e = { ...t, name: this.pName + " " + t.name };
    this.dispatchEvent(
      new CustomEvent("select", {
        detail: { product: e },
        bubbles: !0,
        composed: !0,
      })
    );
  }
  togglePriceCheck(t) {
    const e = t.id;
    this.checkedIds.has(e) ? this.checkedIds.delete(e) : this.checkedIds.add(e),
      this.requestUpdate();
    const i = { ...t, name: `${this.pName} ${t.name}` };
    this.dispatchEvent(
      new CustomEvent("select", {
        detail: {
          categoryId: this.cId,
          categoryChildId: this.ccId,
          product: i,
          checked: this.checkedIds.has(e),
        },
        bubbles: !0,
        composed: !0,
      })
    );
  }
  setChecked(t, e) {
    e ? this.checkedIds.add(t) : this.checkedIds.delete(t),
      this.requestUpdate();
  }
};
E.styles = U`
        .p-item {
            display: flex;
            flex-direction: row;
            padding: var(--size-3);
            background-color: var(--surface-2);
            border-radius: var(--radius-3);
            gap: var(--size-3);
            .p-img-container {
                display: flex;
                align-items: center;
                text-decoration: none;
                flex: 0;
                & .p-img {
                    width: 120px;
                    height: 120px;
                    border-radius: var(--radius-2);
                    background-color: #fff;
                    border: var(--border-size-1) solid var(--surface-3);
                    object-fit: contain;
                }
            }
        }
        .p-content {
            display: flex;
            flex-direction: column;
            flex: 1;
            overflow: hidden;
            padding: var(--size-1) 0;
            min-width: 0;
            justify-content: space-between;

            .p-name {
                flex: 0 1 auto;
                font-size: var(--font-size-1);
                font-weight: var(--font-weight-7);
                color: var(--text-1);
                text-decoration: none;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                &:hover {
                    text-decoration: underline;
                }
            }
            .p-desc {
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;
                overflow: hidden;
                font-size: var(--font-size-0);
                color: var(--text-2);
            }
            .p-reg {
                flex: 0;
                font-size: var(--font-size-0);
                font-weight: var(--font-weight-3);
                color: var(--gray-6);
            }
        }
        .p-prices {
            display: flex;
            flex-direction: column;
            flex: 0;
            min-width: var(--size-13);
            max-width: var(--size-13);
            font-size: var(--font-size-0);
            justify-content: center;
            & .price-item {
                display: flex;
                flex-direction: row;
                justify-content: space-between;
                align-items: center;
                padding: 4px 0;
                font-size: var(--font-size-0);
                color: var(--text-1);
                gap: var(--size-2);
                & .price-name {
                    flex: 1;
                    text-align: left;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                    font-weight: var(--font-weight-7);
                    cursor: pointer;
                    text-decoration: none;
                    color: var(--text-2);
                }
                & .price-value {
                    flex: 0;
                    text-align: right;
                    text-decoration: none;
                    white-space: nowrap;
                    font-weight: var(--font-weight-7);
                    color: #d5cea3;
                    cursor: pointer;
                }
                & .material-icons{
                    user-select: none;
                    -webkit-user-select: none;
                }
                & .price-plus {
                    width: 20px;
                    height: 20px;
                    cursor: pointer;
                    font-size: 20px;
                    color: var(--text-1);
                    transition: 0.1s color ease-in-out;
                    &:hover {
                        color: var(--gray-5);
                    }
                    &.disabled {
                        color: var(--gray-5);
                        cursor: default;
                    }
                    &.checked {
                        width: 20px;
                        height: 20px;
                        font-size: 20px;
                        color: var(--red-6);
                        &:hover {
                            color: var(--red-8);
                        }
                        //width: 16px;
                        //color: var(--red-8);
                    }
                }
            }
        }
    `;
I([x({ type: Number })], E.prototype, "pId", 2);
I([x({ type: Number })], E.prototype, "cId", 2);
I([x({ type: Number })], E.prototype, "ccId", 2);
I([x({ type: String })], E.prototype, "pName", 2);
I([x({ type: String })], E.prototype, "src", 2);
I([x({ type: String })], E.prototype, "desc", 2);
I([x({ type: String })], E.prototype, "pReg", 2);
I([x({ type: Number })], E.prototype, "defaultVariant", 2);
I([x({ type: Array })], E.prototype, "variants", 2);
I([T()], E.prototype, "checkedIds", 2);
E = I([j("product-item")], E);
/**
 * @license
 * Copyright 2020 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ const { I: Ct } = mt,
  Ue = () => document.createComment(""),
  Y = (t, e, i) => {
    var o;
    const r = t._$AA.parentNode,
      s = e === void 0 ? t._$AB : e._$AA;
    if (i === void 0) {
      const n = r.insertBefore(Ue(), s),
        d = r.insertBefore(Ue(), s);
      i = new Ct(n, d, t, t.options);
    } else {
      const n = i._$AB.nextSibling,
        d = i._$AM,
        a = d !== t;
      if (a) {
        let c;
        (o = i._$AQ) == null || o.call(i, t),
          (i._$AM = t),
          i._$AP !== void 0 && (c = t._$AU) !== d._$AU && i._$AP(c);
      }
      if (n !== s || a) {
        let c = i._$AA;
        for (; c !== n; ) {
          const p = c.nextSibling;
          r.insertBefore(c, s), (c = p);
        }
      }
    }
    return i;
  },
  q = (t, e, i = t) => (t._$AI(e, i), t),
  It = {},
  Pt = (t, e = It) => (t._$AH = e),
  Ot = (t) => t._$AH,
  ge = (t) => {
    var r;
    (r = t._$AP) == null || r.call(t, !1, !0);
    let e = t._$AA;
    const i = t._$AB.nextSibling;
    for (; e !== i; ) {
      const s = e.nextSibling;
      e.remove(), (e = s);
    }
  };
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */ const je = (t, e, i) => {
    const r = new Map();
    for (let s = e; s <= i; s++) r.set(t[s], s);
    return r;
  },
  Rt = Ke(
    class extends Je {
      constructor(t) {
        if ((super(t), t.type !== We.CHILD))
          throw Error("repeat() can only be used in text expressions");
      }
      dt(t, e, i) {
        let r;
        i === void 0 ? (i = e) : e !== void 0 && (r = e);
        const s = [],
          o = [];
        let n = 0;
        for (const d of t) (s[n] = r ? r(d, n) : n), (o[n] = i(d, n)), n++;
        return { values: o, keys: s };
      }
      render(t, e, i) {
        return this.dt(t, e, i).values;
      }
      update(t, [e, i, r]) {
        const s = Ot(t),
          { values: o, keys: n } = this.dt(e, i, r);
        if (!Array.isArray(s)) return (this.ut = n), o;
        const d = this.ut ?? (this.ut = []),
          a = [];
        let c,
          p,
          l = 0,
          h = s.length - 1,
          m = 0,
          b = o.length - 1;
        for (; l <= h && m <= b; )
          if (s[l] === null) l++;
          else if (s[h] === null) h--;
          else if (d[l] === n[m]) (a[m] = q(s[l], o[m])), l++, m++;
          else if (d[h] === n[b]) (a[b] = q(s[h], o[b])), h--, b--;
          else if (d[l] === n[b])
            (a[b] = q(s[l], o[b])), Y(t, a[b + 1], s[l]), l++, b--;
          else if (d[h] === n[m])
            (a[m] = q(s[h], o[m])), Y(t, s[l], s[h]), h--, m++;
          else if (
            (c === void 0 && ((c = je(n, m, b)), (p = je(d, l, h))),
            c.has(d[l]))
          )
            if (c.has(d[h])) {
              const _ = p.get(n[m]),
                S = _ !== void 0 ? s[_] : null;
              if (S === null) {
                const w = Y(t, s[l]);
                q(w, o[m]), (a[m] = w);
              } else (a[m] = q(S, o[m])), Y(t, s[l], S), (s[_] = null);
              m++;
            } else ge(s[h]), h--;
          else ge(s[l]), l++;
        for (; m <= b; ) {
          const _ = Y(t, a[b + 1]);
          q(_, o[m]), (a[m++] = _);
        }
        for (; l <= h; ) {
          const _ = s[l++];
          _ !== null && ge(_);
        }
        return (this.ut = n), Pt(t, a), M;
      }
    }
  );
var Mt = Object.defineProperty,
  Ut = Object.getOwnPropertyDescriptor,
  he = (t, e, i, r) => {
    for (
      var s = r > 1 ? void 0 : r ? Ut(e, i) : e, o = t.length - 1, n;
      o >= 0;
      o--
    )
      (n = t[o]) && (s = (r ? n(e, i, s) : n(s)) || s);
    return r && s && Mt(e, i, s), s;
  };
let K = class extends C {
  constructor() {
    super(...arguments), (this.categoryMap = new Map()), (this.products = []);
  }
  firstUpdated(t) {
    this.addEventListener("select", this.handleSelect);
  }
  updated(t) {
    t.has("basketSideEl") &&
      this.basketSideEl &&
      this.basketSideEl.addEventListener("remove", (e) =>
        this.onBasketRemove(e)
      );
  }
  setProducts(t) {
    this.products = t;
  }
  updateProductById(t, e) {
    this.products = this.products.map((i) => (i.id === t ? { ...i, ...e } : i));
  }
  setChecked(t, e, i) {
    const r = this.renderRoot.querySelectorAll("product-item");
    for (const s of r)
      if (s.pId === t) {
        s.setChecked(e, i);
        break;
      }
  }
  handleSelect(t) {
    var l;
    if (!this.basketSideEl || !this.categoryMap) return;
    const {
        categoryId: e,
        categoryChildId: i,
        product: r,
        checked: s,
      } = t.detail,
      o = this.categoryMap.get(e);
    if (!o) return;
    const n = ((l = o.variants.get(i)) == null ? void 0 : l.name) || o.name;
    if (!n) return;
    const d = this.basketSideEl,
      { id: a, name: c = "", price: p } = r;
      // console.log('[ product-list ] a :', a, 'c :', c, 'p :', p);
    
    // 상품의 simpleImg 찾기

    const simpleImg = this.products.find(p => p.id === a) ? this.products.find(p => p.id === a).simpleImg : 
    this.products.find(p => p.variants.find(v => v.id === a)).simpleImg;
    s ? d.addProduct(n, a, c ?? "", p, simpleImg) : d.removeProduct(n, a);
  }
  onBasketRemove(t) {
    if (!this.basketSideEl) return;
    t.detail.product.forEach((i) => {
      const r = Array.from(
        this.renderRoot.querySelectorAll("product-item")
      ).find((s) => {
        var n;
        return (n = s.variants) == null ? void 0 : n.some((d) => d.id === i.id);
      });
      r
        ? r.setChecked(i.id, !1)
        : console.warn("상품을 찾을 수 없습니다 (priceId):", i.id);
    });
  }
  getVariantToPrice(t) {
    return t.variants.length > 0
      ? t.variants.map((e) => ({
          id: e.id,
          name: e.name,
          price: e.price,
          schedule: e.price == 0,
        }))
      : [{ id: t.id, name: null, price: t.price, schedule: t.price == 0 }];
  }
  render() {
    return f`
            <div id="product-list">
                ${Rt(
                  this.products,
                  (t) => t.id,
                  (t) => f`
                  <product-item
                          .pId=${t.id}
                          .cId=${t.categoryId}
                          .ccId=${t.categoryChildId}
                          .pName=${t.name}
                          .src=${t.simpleImg}
                          .desc=${t.desc}
                          .pReg=${t.reg}
                          .defaultVariant=${t.defaultVariant}
                          .variants=${this.getVariantToPrice(t)}
                  ></product-item>
                `
                )}
            </div>
        `;
  }
};
K.styles = U`
        #product-list {
            display: flex;
            flex-direction: column;
            gap: var(--size-1);
        }
    `;
he([x({ type: Object })], K.prototype, "categoryMap", 2);
he([x({ type: Object })], K.prototype, "basketSideEl", 2);
he([x({ type: Array })], K.prototype, "products", 2);
K = he([j("product-list")], K);
function jt(t) {
  return t && t.__esModule && Object.prototype.hasOwnProperty.call(t, "default")
    ? t.default
    : t;
}
var be = { exports: {} },
  Te;
function Tt() {
  return (
    Te ||
      ((Te = 1),
      (function (t) {
        var e = (function () {
          var i = String.fromCharCode,
            r =
              "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
            s =
              "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-$",
            o = {};
          function n(a, c) {
            if (!o[a]) {
              o[a] = {};
              for (var p = 0; p < a.length; p++) o[a][a.charAt(p)] = p;
            }
            return o[a][c];
          }
          var d = {
            compressToBase64: function (a) {
              if (a == null) return "";
              var c = d._compress(a, 6, function (p) {
                return r.charAt(p);
              });
              switch (c.length % 4) {
                default:
                case 0:
                  return c;
                case 1:
                  return c + "===";
                case 2:
                  return c + "==";
                case 3:
                  return c + "=";
              }
            },
            decompressFromBase64: function (a) {
              return a == null
                ? ""
                : a == ""
                ? null
                : d._decompress(a.length, 32, function (c) {
                    return n(r, a.charAt(c));
                  });
            },
            compressToUTF16: function (a) {
              return a == null
                ? ""
                : d._compress(a, 15, function (c) {
                    return i(c + 32);
                  }) + " ";
            },
            decompressFromUTF16: function (a) {
              return a == null
                ? ""
                : a == ""
                ? null
                : d._decompress(a.length, 16384, function (c) {
                    return a.charCodeAt(c) - 32;
                  });
            },
            compressToUint8Array: function (a) {
              for (
                var c = d.compress(a),
                  p = new Uint8Array(c.length * 2),
                  l = 0,
                  h = c.length;
                l < h;
                l++
              ) {
                var m = c.charCodeAt(l);
                (p[l * 2] = m >>> 8), (p[l * 2 + 1] = m % 256);
              }
              return p;
            },
            decompressFromUint8Array: function (a) {
              if (a == null) return d.decompress(a);
              for (
                var c = new Array(a.length / 2), p = 0, l = c.length;
                p < l;
                p++
              )
                c[p] = a[p * 2] * 256 + a[p * 2 + 1];
              var h = [];
              return (
                c.forEach(function (m) {
                  h.push(i(m));
                }),
                d.decompress(h.join(""))
              );
            },
            compressToEncodedURIComponent: function (a) {
              return a == null
                ? ""
                : d._compress(a, 6, function (c) {
                    return s.charAt(c);
                  });
            },
            decompressFromEncodedURIComponent: function (a) {
              return a == null
                ? ""
                : a == ""
                ? null
                : ((a = a.replace(/ /g, "+")),
                  d._decompress(a.length, 32, function (c) {
                    return n(s, a.charAt(c));
                  }));
            },
            compress: function (a) {
              return d._compress(a, 16, function (c) {
                return i(c);
              });
            },
            _compress: function (a, c, p) {
              if (a == null) return "";
              var l,
                h,
                m = {},
                b = {},
                _ = "",
                S = "",
                w = "",
                k = 2,
                A = 3,
                y = 2,
                $ = [],
                u = 0,
                g = 0,
                v;
              for (v = 0; v < a.length; v += 1)
                if (
                  ((_ = a.charAt(v)),
                  Object.prototype.hasOwnProperty.call(m, _) ||
                    ((m[_] = A++), (b[_] = !0)),
                  (S = w + _),
                  Object.prototype.hasOwnProperty.call(m, S))
                )
                  w = S;
                else {
                  if (Object.prototype.hasOwnProperty.call(b, w)) {
                    if (w.charCodeAt(0) < 256) {
                      for (l = 0; l < y; l++)
                        (u = u << 1),
                          g == c - 1 ? ((g = 0), $.push(p(u)), (u = 0)) : g++;
                      for (h = w.charCodeAt(0), l = 0; l < 8; l++)
                        (u = (u << 1) | (h & 1)),
                          g == c - 1 ? ((g = 0), $.push(p(u)), (u = 0)) : g++,
                          (h = h >> 1);
                    } else {
                      for (h = 1, l = 0; l < y; l++)
                        (u = (u << 1) | h),
                          g == c - 1 ? ((g = 0), $.push(p(u)), (u = 0)) : g++,
                          (h = 0);
                      for (h = w.charCodeAt(0), l = 0; l < 16; l++)
                        (u = (u << 1) | (h & 1)),
                          g == c - 1 ? ((g = 0), $.push(p(u)), (u = 0)) : g++,
                          (h = h >> 1);
                    }
                    k--, k == 0 && ((k = Math.pow(2, y)), y++), delete b[w];
                  } else
                    for (h = m[w], l = 0; l < y; l++)
                      (u = (u << 1) | (h & 1)),
                        g == c - 1 ? ((g = 0), $.push(p(u)), (u = 0)) : g++,
                        (h = h >> 1);
                  k--,
                    k == 0 && ((k = Math.pow(2, y)), y++),
                    (m[S] = A++),
                    (w = String(_));
                }
              if (w !== "") {
                if (Object.prototype.hasOwnProperty.call(b, w)) {
                  if (w.charCodeAt(0) < 256) {
                    for (l = 0; l < y; l++)
                      (u = u << 1),
                        g == c - 1 ? ((g = 0), $.push(p(u)), (u = 0)) : g++;
                    for (h = w.charCodeAt(0), l = 0; l < 8; l++)
                      (u = (u << 1) | (h & 1)),
                        g == c - 1 ? ((g = 0), $.push(p(u)), (u = 0)) : g++,
                        (h = h >> 1);
                  } else {
                    for (h = 1, l = 0; l < y; l++)
                      (u = (u << 1) | h),
                        g == c - 1 ? ((g = 0), $.push(p(u)), (u = 0)) : g++,
                        (h = 0);
                    for (h = w.charCodeAt(0), l = 0; l < 16; l++)
                      (u = (u << 1) | (h & 1)),
                        g == c - 1 ? ((g = 0), $.push(p(u)), (u = 0)) : g++,
                        (h = h >> 1);
                  }
                  k--, k == 0 && ((k = Math.pow(2, y)), y++), delete b[w];
                } else
                  for (h = m[w], l = 0; l < y; l++)
                    (u = (u << 1) | (h & 1)),
                      g == c - 1 ? ((g = 0), $.push(p(u)), (u = 0)) : g++,
                      (h = h >> 1);
                k--, k == 0 && ((k = Math.pow(2, y)), y++);
              }
              for (h = 2, l = 0; l < y; l++)
                (u = (u << 1) | (h & 1)),
                  g == c - 1 ? ((g = 0), $.push(p(u)), (u = 0)) : g++,
                  (h = h >> 1);
              for (;;)
                if (((u = u << 1), g == c - 1)) {
                  $.push(p(u));
                  break;
                } else g++;
              return $.join("");
            },
            decompress: function (a) {
              return a == null
                ? ""
                : a == ""
                ? null
                : d._decompress(a.length, 32768, function (c) {
                    return a.charCodeAt(c);
                  });
            },
            _decompress: function (a, c, p) {
              var l = [],
                h = 4,
                m = 4,
                b = 3,
                _ = "",
                S = [],
                w,
                k,
                A,
                y,
                $,
                u,
                g,
                v = { val: p(0), position: c, index: 1 };
              for (w = 0; w < 3; w += 1) l[w] = w;
              for (A = 0, $ = Math.pow(2, 2), u = 1; u != $; )
                (y = v.val & v.position),
                  (v.position >>= 1),
                  v.position == 0 && ((v.position = c), (v.val = p(v.index++))),
                  (A |= (y > 0 ? 1 : 0) * u),
                  (u <<= 1);
              switch (A) {
                case 0:
                  for (A = 0, $ = Math.pow(2, 8), u = 1; u != $; )
                    (y = v.val & v.position),
                      (v.position >>= 1),
                      v.position == 0 &&
                        ((v.position = c), (v.val = p(v.index++))),
                      (A |= (y > 0 ? 1 : 0) * u),
                      (u <<= 1);
                  g = i(A);
                  break;
                case 1:
                  for (A = 0, $ = Math.pow(2, 16), u = 1; u != $; )
                    (y = v.val & v.position),
                      (v.position >>= 1),
                      v.position == 0 &&
                        ((v.position = c), (v.val = p(v.index++))),
                      (A |= (y > 0 ? 1 : 0) * u),
                      (u <<= 1);
                  g = i(A);
                  break;
                case 2:
                  return "";
              }
              for (l[3] = g, k = g, S.push(g); ; ) {
                if (v.index > a) return "";
                for (A = 0, $ = Math.pow(2, b), u = 1; u != $; )
                  (y = v.val & v.position),
                    (v.position >>= 1),
                    v.position == 0 &&
                      ((v.position = c), (v.val = p(v.index++))),
                    (A |= (y > 0 ? 1 : 0) * u),
                    (u <<= 1);
                switch ((g = A)) {
                  case 0:
                    for (A = 0, $ = Math.pow(2, 8), u = 1; u != $; )
                      (y = v.val & v.position),
                        (v.position >>= 1),
                        v.position == 0 &&
                          ((v.position = c), (v.val = p(v.index++))),
                        (A |= (y > 0 ? 1 : 0) * u),
                        (u <<= 1);
                    (l[m++] = i(A)), (g = m - 1), h--;
                    break;
                  case 1:
                    for (A = 0, $ = Math.pow(2, 16), u = 1; u != $; )
                      (y = v.val & v.position),
                        (v.position >>= 1),
                        v.position == 0 &&
                          ((v.position = c), (v.val = p(v.index++))),
                        (A |= (y > 0 ? 1 : 0) * u),
                        (u <<= 1);
                    (l[m++] = i(A)), (g = m - 1), h--;
                    break;
                  case 2:
                    return S.join("");
                }
                if ((h == 0 && ((h = Math.pow(2, b)), b++), l[g])) _ = l[g];
                else if (g === m) _ = k + k.charAt(0);
                else return null;
                S.push(_),
                  (l[m++] = k + _.charAt(0)),
                  h--,
                  (k = _),
                  h == 0 && ((h = Math.pow(2, b)), b++);
              }
            },
          };
          return d;
        })();
        t != null
          ? (t.exports = e)
          : typeof angular < "u" &&
            angular != null &&
            angular.module("LZString", []).factory("LZString", function () {
              return e;
            });
      })(be)),
    be.exports
  );
}
var Lt = Tt();
const Le = jt(Lt);
var Dt = Object.defineProperty,
  qt = Object.getOwnPropertyDescriptor,
  _e = (t, e, i, r) => {
    for (
      var s = r > 1 ? void 0 : r ? qt(e, i) : e, o = t.length - 1, n;
      o >= 0;
      o--
    )
      (n = t[o]) && (s = (r ? n(e, i, s) : n(s)) || s);
    return r && s && Dt(e, i, s), s;
  };
let se = class extends C {
  constructor() {
    super(...arguments), (this.category = []), (this.basket = new Map());
  }
  addProduct(t, e, i, r, s) {
    let n = [];
    if (
      (this.basket.has(t) && (n = this.basket.get(t)),
      !n.some((o) => o.id === e))
    )
      n.push({ id: e, name: i, price: r, simpleImg: s, amount: 1 });
    else {
      let o = this.getProductAmount(n, e);
      o != null && (o++, (n = this.updateAmountImmutable(n, e, o)));
    }
    this.basket.set(t, n), this.requestUpdate();
  }
  minusProduct(t, e) {
    let i = [];
    if (
      (this.basket.has(t) && (i = this.basket.get(t)),
      i.some((s) => s.id === e))
    ) {
      let s = this.getProductAmount(i, e);
      if (s)
        if (s > 1)
          s--, this.updateAmountImmutable(i, e, s), this.dispatchChange();
        else {
          const o = this.findById(i, e);
          (i = this.removeById(i, e)), o && this.dispatchRemove(o);
        }
    }
    this.basket.set(t, i), this.requestUpdate();
  }
  removeProduct(t, e) {
    let i = [];
    if (
      (this.basket.has(t) && (i = this.basket.get(t)),
      i.some((s) => s.id === e))
    ) {
      const s = this.findById(i, e);
      (i = this.removeById(i, e)), s && this.dispatchRemove(s);
    }
    if (i.length == 0) {
      this.basket.delete(t), this.requestUpdate();
      return;
    }
    this.basket.set(t, i), this.requestUpdate();
  }
  productClear() {
    this.dispatchRemoves(), this.basket.clear(), this.requestUpdate();
  }
  getProductAmount(t, e) {
    const i = t.find((r) => r.id === e);
    return i != null ? i.amount : null;
  }
  updateAmountImmutable(t, e, i) {
    return t.map((r) => (r.id === e ? { ...r, amount: i } : r));
  }
  findById(t, e) {
    return t.find((i) => i.id === e);
  }
  removeById(t, e) {
    return t.filter((i) => i.id !== e);
  }
  get calculateTotalPrice() {
    let t = 0;
    for (const [, e] of this.basket) for (const i of e) t += i.price * i.amount;
    return t;
  }
  get compressedData() {
    const t = Array.from(this.basket.entries()),
      e = JSON.stringify(t);
    return Le.compressToBase64(e);
  }
  set decompressedData(t) {
    const e = Le.decompressFromBase64(t);
    if (!e) throw new Error("압축 해제 실패");
    const i = JSON.parse(e);
    this.basket = new Map(Object.entries(i));
  }
  dispatchChange() {
    const t = this.basket;
    this.dispatchEvent(
      new CustomEvent("change", {
        detail: { basket: t },
        bubbles: !0,
        composed: !0,
      })
    );
  }
  dispatchRemove(t) {
    const e = { id: t.id, price: t.price, name: t.name, schedule: !0 };
    this.dispatchEvent(
      new CustomEvent("remove", {
        detail: { product: [e] },
        bubbles: !0,
        composed: !0,
      })
    );
  }
  dispatchRemoves() {
    const t = Array.from(this.basket.values())
      .flat()
      .map((e) => ({ id: e.id, name: e.name, price: e.price, schedule: !0 }));
    this.dispatchEvent(new CustomEvent("remove", { detail: { product: t } }));
  }
  handleInput(t, e, i) {
    const r = t.target,
      s = Number(r.value);
    if (s < 1 || isNaN(s)) return;
    const o = this.basket.get(e);
    if (!o) return;
    const n = o.map((d) => (d.id === i ? { ...d, amount: s } : d));
    this.basket.set(e, n), this.requestUpdate();
  }

  handlePurchase() {
    if (this.basket.size === 0) {
      alert('장바구니가 비어있습니다.');
      return;
    }

    // 폼 생성
    const f = document.createElement('form');
    f.method = 'POST';
    f.action = '/cart/checkout';

    // 선택된 상품들 추가
    let i = 0;
    for (const [n, s] of this.basket) {
      for (const p of s) {
        // selectedProducts 배열에 상품 ID 추가
        const t = document.createElement('input');
        t.type = 'hidden';
        t.name = 'selectedProducts';
        t.value = p.id;
        f.appendChild(t);

        // quantities 배열에 수량 추가
        const q = document.createElement('input');
        q.type = 'hidden';
        q.name = `quantities[${p.id}]`;
        q.value = p.amount;
        f.appendChild(q);

        i++;
      }
    }

    // 폼을 body에 추가하고 제출
    document.body.appendChild(f);
    f.submit();
  }
  basketContentRenderer() {
    return this.basket.size === 0
      ? f`
      <div id="basket-empty">
        비어있습니다
      </div>
    `
      : this.category.length > 0 &&
        this.category.some((e) => this.basket.has(e.code))
      ? f`
      ${this.category
        .filter((e) => this.basket.has(e.code))
        .sort((e, i) => e.id - i.id)
        .map((e) => {
          const i = this.basket.get(e.code);
          return f`
            <div class="basket-category">${e.name}</div>
            ${i.map(
              (r) => f`
              <div class="basket-item">
                <div class="b-img">
                  <img src="/pdr/img/${r.simpleImg || 'common.jpeg'}" 
                       onerror="this.onerror=null; this.src='https://placehold.co/600x400';"
                       alt="${r.id}" />
                </div>
                <div class="b-content">
                  <div class="b-pName" title=${r.name}>${r.name}</div>
                  <div class="bc-bottom">
                    <div class="b-pPrice">${r.price.toLocaleString()}원</div>
                    <div class="b-pAmount">수량 <input type="number" value=${
                      r.amount
                    } min="1" max="1000"
                                                     @input=${(s) =>
                                                       this.handleInput(
                                                         s,
                                                         e.name,
                                                         r.id
                                                       )}></div>
                  </div>
                </div>
                <div class="b-remove" @click=${() =>
                  this.removeProduct(e.code, r.id)}>
                  <span class="material-icons">close</span>
                </div>
              </div>
            `
            )}
          `;
        })}
    `
      : f`
    ${Array.from(this.basket.entries()).map(
      ([e, i]) => f`
        <div class="basket-category">${e.toUpperCase()}</div>
        ${i.map(
          (r) => f`
          <div class="basket-item">
            <div class="b-img">
              <img src="/pdr/img/${r.simpleImg || 'common.jpeg'}" 
                   onerror="this.onerror=null; this.src='https://placehold.co/600x400';"
                   alt="${r.id}" />
            </div>
            <div class="b-content">
              <div class="b-pName" title=${r.name}>${r.name}</div>
              <div class="bc-bottom">
                <div class="b-pPrice">${r.price.toLocaleString()}원</div>
                <div class="b-pAmount">수량 <input type="number" value=${
                  r.amount
                } min="1" max="1000"
                                                 @input=${(s) =>
                                                   this.handleInput(
                                                     s,
                                                     e,
                                                     r.id
                                                   )}></div>
              </div>
            </div>
            <div class="b-remove" @click=${() =>
              this.removeProduct(
                e,
                r.id
              )}><span class="material-icons">close</span></div>
          </div>
        `
        )}
      `
    )}
  `;
  }
  render() {
    return f`
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <div id="basket-container">
            <div id="basket-header">장바구니</div>
            <div id="basket-content">
                ${this.basketContentRenderer()}
            </div>
            <div id="basket-total-price">
                <div id="price-title">총 금액</div>
                <div id="total-price">${this.calculateTotalPrice.toLocaleString()}원</div>
            </div>
            <div id="basket-buttons">
                <span class="material-icons" id="share">share</span>
               
                <div id="reset" class="btn" @click=${() =>
                  this.productClear()}>초기화</div>
                <div id="buy" class="btn" @click=${() =>
                  this.handlePurchase()}>구매</div>
            </div>
        </div>
    `;
  }
};
se.styles = U`
    #basket-container {
      width: var(--size-14);
      display: flex;
      flex-direction: column;
      padding: var(--size-2) 0 var(--size-3) 0;
      background: var(--surface-2);
      border-radius: var(--radius-3);
      gap: var(--size-1);
      #basket-header {
        display: flex;
        justify-content: center;
        font-weight: var(--font-weight-7);
        padding-bottom: var(--size-1);
        margin: 0 var(--size-2);
        border-bottom: var(--border-size-1) solid var(--surface-3);
        user-select: none;
        -webkit-user-select: none;
      }
      #basket-total-price{
        display: flex;
        justify-content: flex-end;
        margin: 0 var(--size-2);
        padding: var(--size-1) 0 0 0;
        border-top: var(--border-size-1) solid var(--surface-3);
        gap: var(--size-2);
        cursor: default;
        #total-price {
          font-weight: var(--font-weight-7);
          color: #d5cea3;
        }
      }
      #basket-buttons {
        display: flex;
        flex-direction: row;
        justify-content: center;
        gap: var(--size-1);
        user-select: none;
        -webkit-user-select: none;
        margin: 0 var(--size-2);
        padding: var(--size-2) 0 0 0;
        font-weight: var(--font-weight-7);
        border-top: var(--border-size-1) solid var(--surface-3);
        #share {
          width: var(--size-7);
          height: var(--size-7);
          background-color: var(--gray-6);
          border-radius: var(--radius-2);
          text-align: center;
          align-content: center;
          cursor: pointer;
          transition: 0.1s ease-in-out background-color;
          &:hover {
            background-color: var(--gray-5);
          }
        }
        #check {
          background-color: var(--teal-7);
          &:hover {
            background-color: var(--teal-8);
          }
        }
        #reset {
          background-color: var(--red-7);
          &:hover {
            background-color: var(--red-8);
          }
        }
        #buy {
          background-color: var(--lime-7);
          &:hover {
            background-color: var(--lime-8);
          }
        }
        .btn {
          text-align: center;
          width: var(--size-9);
          padding: var(--size-1) 0;
          border-radius: var(--radius-2);
          cursor: pointer;
          transition: 0.1s ease-in-out background-color;
        }
      }
    }
    
    #basket-empty {
      display: flex;
      justify-content: center;
      padding: var(--size-2);
      margin: 0 var(--size-2);
      border-radius: var(--radius-2);
      background-color: var(--surface-3);
      user-select: none;
    }
    
    #basket-content {
      display: flex;
      flex-direction: column;
      gap: var(--size-1);
      .basket-category {
        padding: 0 var(--size-3);
        font-weight: var(--font-weight-7);
      }
      .basket-item {
        display: flex;
        flex-direction: row;
        margin: 0 var(--size-2);
        padding: 0 var(--size-1);
        border: var(--border-size-1) var(--gray-0);
        border-style: dashed;
        border-radius: var(--radius-2);
        .b-img {
          display: flex;
          flex-direction: column;
          flex: 0;
          justify-content: center;
          & img {
            width: var(--size-7);
            height: var(--size-7);
            object-fit: contain;
            background-color: var(--surface-4);
          }
        }
        .b-content {
          display: flex;
          flex-direction: column;
          flex: 1;
          padding: 0 var(--size-2);
          overflow: hidden;
          .b-pName {
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            font-size: var(--font-size-1);
            font-weight: var(--font-weight-7);
            cursor: pointer;
            &:hover{
              text-decoration: underline;
            }
          }
          .bc-bottom {
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            font-size: var(--font-size-0);
            .b-pPrice{
              flex: 1;
            }
            .b-pAmount input[type="number"]{
              width: var(--size-8);
            }
          }
        }
        .b-remove {
          display: flex;
          flex-direction: column;
          justify-content: center;
          user-select: none;
          -webkit-user-select: none;
          cursor: pointer;
          transition: 0.1s color ease-in-out;
          &:hover {
            color: var(--gray-5);
          }
        }
      }
    }
  `;
_e([x({ type: Array })], se.prototype, "category", 2);
_e([x({ type: Map })], se.prototype, "basket", 2);
se = _e([j("basket-side")], se);
var Nt = Object.defineProperty,
  Ft = Object.getOwnPropertyDescriptor,
  Ae = (t, e, i, r) => {
    for (
      var s = r > 1 ? void 0 : r ? Ft(e, i) : e, o = t.length - 1, n;
      o >= 0;
      o--
    )
      (n = t[o]) && (s = (r ? n(e, i, s) : n(s)) || s);
    return r && s && Nt(e, i, s), s;
  };
let re = class extends C {
  constructor() {
    super(...arguments),
      (this.filterRows = []),
      (this.expanded = !1),
      (this.dispatchFilterChange = () => {
        const t = {};
        this.renderRoot
          .querySelectorAll('input[type="checkbox"]:checked')
          .forEach((i) => {
            const r = i.name,
              s = i.value;
            t[r] || (t[r] = []), t[r].push(this.deserializeValue(s));
          }),
          this.dispatchEvent(
            new CustomEvent("change", { detail: t, bubbles: !0, composed: !0 })
          );
      });
  }
  deserializeValue(t) {
    try {
      return JSON.parse(t);
    } catch {
      return t;
    }
  }
  render() {
    const t = this.filterRows
      .filter((e) => e.isFilterable)
      .sort((e, i) => e.displayOrder - i.displayOrder);
    return f`
      <div class="filter-container">
        ${t.map(
          (e) => f`
          <div class="filter-row">
            <div class="filter-label" title=${e.tooltip ?? ""}>
              ${e.displayName}
            </div>
            <div class="filter-options ${this.expanded ? "" : "collapsed"}">
              ${e.valueList
                .slice()
                .sort((i, r) => r.weight - i.weight)
                .map((i) => {
                  let r;
                  if (typeof i.value == "object") r = JSON.stringify(i.value);
                  else {
                    const s = Number(i.value);
                    if (e.unit === "GB" && !isNaN(s))
                      if (s >= 1e3) {
                        const o = s / 1e3;
                        r = `${o % 1 === 0 ? o : o.toFixed(1)}TB`;
                      } else r = `${s}GB`;
                    else r = `${i.value}${e.unit ?? ""}`;
                  }
                  return f`
                    <label>
                      <input
                        type="checkbox"
                        name=${e.attributeKey}
                        .value=${JSON.stringify(i.value)}
                        @change=${this.dispatchFilterChange}>
                      ${r}
                    </label>
                  `;
                })}
            </div>
          </div>
        `
        )}
        <div class="filter-bottom" @click=${() =>
          (this.expanded = !this.expanded)}>
          ${this.expanded ? "접기" : "펼치기"}
        </div>
      </div>
    `;
  }
};
re.styles = U`
    :host {
      display: block;
      font-family: sans-serif;
    }

    .filter-container {
      display: flex;
      flex-direction: column;
      border: 1px solid var(--surface-4);
      border-radius: var(--radius-2);
      overflow: hidden;
      font-size: 13px;
    }

    .filter-row {
      display: flex;
      align-items: stretch;
      border-top: 1px solid var(--surface-4);
    }

    .filter-row:first-child {
      border-top: none;
    }

    .filter-label {
      width: 120px;
      max-width: 120px;
      font-weight: bold;
      padding: var(--size-2) var(--size-3);
      background-color: var(--surface-1);
    }

    .filter-options {
      flex: 1;
      display: flex;
      flex-wrap: wrap;
      gap: var(--size-1);
      padding: var(--size-2);
      transition: max-height 0.3s ease;
    }

    .filter-options.collapsed {
      max-height: calc(2 * 32px);
      overflow: hidden;
    }

    .filter-options label {
      display: flex;
      align-items: center;
      gap: 4px;
      cursor: pointer;
    }

    .filter-bottom {
      display: flex;
      justify-content: center;
      background: var(--surface-2);
      user-select: none;
      cursor: pointer;
    }

    .filter-bottom:hover {
      background: var(--surface-3);
    }
  `;
Ae([x({ type: Array })], re.prototype, "filterRows", 2);
Ae([x({ type: Boolean })], re.prototype, "expanded", 2);
re = Ae([j("filter-panel")], re);
var Bt = Object.defineProperty,
  Ht = Object.getOwnPropertyDescriptor,
  L = (t, e, i, r) => {
    for (
      var s = r > 1 ? void 0 : r ? Ht(e, i) : e, o = t.length - 1, n;
      o >= 0;
      o--
    )
      (n = t[o]) && (s = (r ? n(e, i, s) : n(s)) || s);
    return r && s && Bt(e, i, s), s;
  };
const De = f`
    <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#e3e3e3">
        <path d="m600-200-56-57 143-143H300q-75 0-127.5-52.5T120-580q0-75 52.5-127.5T300-760h20v80h-20q-42 0-71 29t-29 71q0 42 29 71t71 29h387L544-624l56-56 240 240-240 240Z"/>
    </svg>
`;
let P = class extends C {
  constructor() {
    super(...arguments),
      (this.comments = []),
      (this.replyOpenMap = new Map()),
      (this.imageFiles = new Map()),
      (this.currentUserId = ""),
      (this.editingCommentId = null),
      (this.editingReplyId = null),
      (this.editImageFiles = new Map());
  }
  requestReply(t) {
    this.dispatchEvent(
      new CustomEvent("request-reply-open", {
        detail: { commentId: t },
        bubbles: !0,
        composed: !0,
      })
    );
  }
  allowReply(t) {
    (this.replyOpenMap = new Map(this.replyOpenMap.set(t, !0))),
      this.imageFiles.set(t, []),
      this.requestUpdate();
  }
  cancelReply(t) {
    this.replyOpenMap.delete(t),
      this.imageFiles.delete(t),
      (this.replyOpenMap = new Map(this.replyOpenMap));
  }
  handleImageClick(t, e, i, r, s, o = !1) {
    var a;
    t.preventDefault(), t.stopPropagation();
    let n = [],
      d = [];
    if (o) {
      const c =
        (a = this.comments.find((p) => p.replies.some((l) => l.id === r))) ==
        null
          ? void 0
          : a.replies.find((p) => p.id === r);
      c != null &&
        c.images &&
        ((n = c.images), (d = c.images.map((p, l) => `답글 이미지 ${l + 1}`)));
    } else {
      const c = this.comments.find((p) => p.id === r);
      c != null &&
        c.images &&
        ((n = c.images), (d = c.images.map((p, l) => `댓글 이미지 ${l + 1}`)));
    }
    n.length > 0 && this.showImageModal(n, d, s);
  }
  showImageModal(t, e, i) {
    const r = document.querySelector(".image-modal");
    r && document.body.removeChild(r);
    const s = document.createElement("div");
    (s.className = "image-modal"),
      (s.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.95);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 10000;
            cursor: pointer;
        `);
    const o = document.createElement("div");
    o.style.cssText = `
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
            width: 100%;
            height: 100%;
        `;
    const n = document.createElement("img");
    if (
      ((n.src = t[i] || ""),
      (n.style.cssText = `
            max-width: 80%;
            max-height: 80%;
            object-fit: contain;
            border-radius: 8px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.5);
        `),
      t.length > 1)
    ) {
      const p = document.createElement("button");
      (p.innerHTML = "‹"),
        (p.style.cssText = `
                position: absolute;
                left: 20px;
                top: 50%;
                transform: translateY(-50%);
                background: rgba(255, 255, 255, 0.2);
                color: white;
                border: none;
                border-radius: 50%;
                width: 50px;
                height: 50px;
                font-size: 30px;
                cursor: pointer;
                display: flex;
                align-items: center;
                justify-content: center;
                transition: background-color 0.2s ease;
                z-index: 10001;
            `),
        (p.onmouseover = () =>
          (p.style.background = "rgba(255, 255, 255, 0.3)")),
        (p.onmouseout = () =>
          (p.style.background = "rgba(255, 255, 255, 0.2)")),
        (p.onclick = (m) => {
          m.stopPropagation();
          const b = i > 0 ? i - 1 : t.length - 1;
          (n.src = t[b] || ""), d && (d.textContent = e[b] || ""), (i = b);
        }),
        o.appendChild(p);
      const l = document.createElement("button");
      (l.innerHTML = "›"),
        (l.style.cssText = `
                position: absolute;
                right: 20px;
                top: 50%;
                transform: translateY(-50%);
                background: rgba(255, 255, 255, 0.2);
                color: white;
                border: none;
                border-radius: 50%;
                width: 50px;
                height: 50px;
                font-size: 30px;
                cursor: pointer;
                display: flex;
                align-items: center;
                justify-content: center;
                transition: background-color 0.2s ease;
                z-index: 10001;
            `),
        (l.onmouseover = () =>
          (l.style.background = "rgba(255, 255, 255, 0.3)")),
        (l.onmouseout = () =>
          (l.style.background = "rgba(255, 255, 255, 0.2)")),
        (l.onclick = (m) => {
          m.stopPropagation();
          const b = i < t.length - 1 ? i + 1 : 0;
          (n.src = t[b] || ""), d && (d.textContent = e[b] || ""), (i = b);
        }),
        o.appendChild(l);
      const h = document.createElement("div");
      (h.textContent = `${i + 1} / ${t.length}`),
        (h.style.cssText = `
                position: absolute;
                top: 20px;
                left: 50%;
                transform: translateX(-50%);
                color: white;
                font-size: 16px;
                background: rgba(0, 0, 0, 0.7);
                padding: 8px 16px;
                border-radius: 20px;
                z-index: 10001;
            `),
        o.appendChild(h);
    }
    const d = document.createElement("div");
    (d.textContent = e[i] || ""),
      (d.style.cssText = `
            position: absolute;
            bottom: 20px;
            left: 50%;
            transform: translateX(-50%);
            color: white;
            font-size: 16px;
            background: rgba(0, 0, 0, 0.7);
            padding: 8px 16px;
            border-radius: 4px;
            z-index: 10001;
        `),
      o.appendChild(n),
      o.appendChild(d),
      s.appendChild(o),
      s.addEventListener("click", () => {
        document.body.removeChild(s);
      });
    let a = i;
    const c = (p) => {
      if (t.length > 1) {
        if (p.key === "ArrowLeft") {
          p.preventDefault(),
            (a = a > 0 ? i - 1 : t.length - 1),
            (n.src = t[a] || ""),
            d && (d.textContent = e[a] || "");
          const l = s.querySelector('div[style*="top: 20px"]');
          l && (l.textContent = `${a + 1} / ${t.length}`);
        } else if (p.key === "ArrowRight") {
          p.preventDefault(),
            (a = a < t.length - 1 ? i + 1 : 0),
            (n.src = t[a] || ""),
            d && (d.textContent = e[a] || "");
          const l = s.querySelector('div[style*="top: 20px"]');
          l && (l.textContent = `${a + 1} / ${t.length}`);
        }
      }
      p.key === "Escape" &&
        (document.body.removeChild(s),
        document.removeEventListener("keydown", c));
    };
    document.addEventListener("keydown", c), document.body.appendChild(s);
  }
  addImages(t) {
    const e = document.createElement("input");
    (e.type = "file"),
      (e.accept = "image/*"),
      (e.multiple = !0),
      (e.onchange = (i) => {
        const r = Array.from(i.target.files || []),
          o = [...(this.imageFiles.get(t) || []), ...r];
        this.imageFiles.set(t, o), this.requestUpdate();
      }),
      e.click();
  }
  addEditImages(t) {
    const e = document.createElement("input");
    (e.type = "file"),
      (e.accept = "image/*"),
      (e.multiple = !0),
      (e.onchange = (i) => {
        const r = Array.from(i.target.files || []),
          o = [...(this.editImageFiles.get(t) || []), ...r];
        this.editImageFiles.set(t, o), this.requestUpdate();
      }),
      e.click();
  }
  removeImage(t, e) {
    const r = (this.imageFiles.get(t) || []).filter((s, o) => o !== e);
    this.imageFiles.set(t, r), this.requestUpdate();
  }
  removeEditImage(t, e) {
    const r = (this.editImageFiles.get(t) || []).filter((s, o) => o !== e);
    this.editImageFiles.set(t, r), this.requestUpdate();
  }
  submitReply(t) {
    const e = this.renderRoot.querySelector(`#editor-${t}`);
    if (!e) return;
    console.log("submitReply", t);
    const i = e.value.trim(),
      r = this.imageFiles.get(t) || [];
    (!i && r.length === 0) ||
      (this.dispatchEvent(
        new CustomEvent("reply-submitted", {
          detail: { commentId: t, content: i, images: r },
          bubbles: !0,
          composed: !0,
        })
      ),
      this.cancelReply(t));
  }
  submitEdit(t) {
    const e = this.renderRoot.querySelector(`#edit-editor-${t}`);
    if (!e) {
      console.error("수정 textarea를 찾을 수 없습니다:", `#edit-editor-${t}`);
      return;
    }
    const i = e.value.trim(),
      r = this.editImageFiles.get(t) || [];
    if (
      (console.log("수정 제출:", { commentId: t, content: i, images: r }),
      !i && r.length === 0)
    ) {
      alert("내용을 입력해주세요.");
      return;
    }
    let s = !1;
    const o = this.comments.findIndex((n) => n.id === t);
    if (o !== -1 && this.comments[o]) {
      if (
        (console.log("댓글 수정:", o),
        (this.comments[o].content = i),
        r.length > 0)
      ) {
        const n = r.map((a, c) => URL.createObjectURL(a)),
          d = this.comments[o].images || [];
        this.comments[o].images = [...d, ...n];
      }
      s = !0;
    } else
      for (let n = 0; n < this.comments.length; n++) {
        const d = this.comments[n];
        if (d != null && d.replies) {
          const a = d.replies.findIndex((c) => c.id === t);
          if (a !== -1 && d.replies[a]) {
            if (
              (console.log("답글 수정:", n, a),
              (d.replies[a].content = i),
              r.length > 0)
            ) {
              const c = r.map((l, h) => URL.createObjectURL(l)),
                p = d.replies[a].images || [];
              d.replies[a].images = [...p, ...c];
            }
            s = !0;
            break;
          }
        }
      }
    s
      ? ((this.comments = [...this.comments]),
        console.log("수정 완료, 새로운 데이터:", this.comments),
        alert("수정이 완료되었습니다!"))
      : (console.error("수정할 댓글/답글을 찾을 수 없습니다:", t),
        alert("수정할 댓글을 찾을 수 없습니다.")),
      this.cancelEdit(t);
  }
  cancelEdit(t) {
    (this.editingCommentId = null),
      (this.editingReplyId = null),
      this.editImageFiles.delete(t),
      (this.editImageFiles = new Map(this.editImageFiles)),
      this.requestUpdate();
  }
  formatDate(t) {
    const e = new Date(t),
      r = new Date().getTime() - e.getTime(),
      s = Math.floor(r / (1e3 * 60)),
      o = Math.floor(r / (1e3 * 60 * 60)),
      n = Math.floor(r / (1e3 * 60 * 60 * 24));
    return s < 1
      ? "방금 전"
      : s < 60
      ? `${s}분 전`
      : o < 24
      ? `${o}시간 전`
      : n < 7
      ? `${n}일 전`
      : e.toLocaleDateString("ko-KR", {
          year: "numeric",
          month: "long",
          day: "numeric",
        }) +
        " " +
        e.toLocaleTimeString("ko-KR", { hour: "2-digit", minute: "2-digit" });
  }
  isAuthor(t) {
    return t.userId === this.currentUserId;
  }
  editComment(t) {
    (this.editingCommentId = t),
      (this.editingReplyId = null),
      this.editImageFiles.set(t, []),
      (this.editImageFiles = new Map(this.editImageFiles)),
      this.requestUpdate();
  }
  editReply(t) {
    (this.editingReplyId = t),
      (this.editingCommentId = null),
      this.editImageFiles.set(t, []),
      (this.editImageFiles = new Map(this.editImageFiles)),
      this.requestUpdate();
  }
  deleteComment(t) {
    if (confirm("정말로 삭제하시겠습니까?")) {
      let e = !1;
      const i = this.comments.findIndex((r) => r.id === t);
      if (i !== -1) this.comments.splice(i, 1), (e = !0);
      else
        for (let r = 0; r < this.comments.length; r++) {
          const s = this.comments[r];
          if (s != null && s.replies) {
            const o = s.replies.findIndex((n) => n.id === t);
            if (o !== -1) {
              s.replies.splice(o, 1), (e = !0);
              break;
            }
          }
        }
      e
        ? ((this.comments = [...this.comments]),
          alert("삭제가 완료되었습니다!"),
          console.log("삭제 완료:", { commentId: t }))
        : alert("삭제할 댓글을 찾을 수 없습니다.");
    }
  }
  addReview(t, e, i, r) {
    const s = Math.max(...this.comments.map((n) => n.id), 0) + 1,
      o = {
        id: s,
        author: t,
        content: e,
        type: "review",
        createdAt: new Date().toISOString(),
        userId: r || this.currentUserId,
        images: i || [],
        replies: [],
      };
    return (this.comments = [o, ...this.comments]), s;
  }
  addQuestion(t, e, i, r) {
    const s = Math.max(...this.comments.map((n) => n.id), 0) + 1,
      o = {
        id: s,
        author: t,
        content: e,
        type: "question",
        createdAt: new Date().toISOString(),
        userId: r || this.currentUserId,
        images: i || [],
        replies: [],
      };
    return (this.comments = [o, ...this.comments]), s;
  }
  addAnswer(t, e, i, r, s) {
    const o = this.comments.find((a) => a.id === t);
    if (!o)
      return console.error("답변을 추가할 댓글을 찾을 수 없습니다:", t), null;
    const n =
        Math.max(
          ...this.comments.flatMap((a) => a.replies.map((c) => c.id)),
          0
        ) + 1,
      d = {
        id: n,
        author: e,
        content: i,
        type: "answer",
        createdAt: new Date().toISOString(),
        userId: s || this.currentUserId,
        images: r || [],
        replies: [],
      };
    return (
      (o.replies = [...o.replies, d]), (this.comments = [...this.comments]), n
    );
  }
  updateComment(t, e, i) {
    const r = this.comments.findIndex((s) => s.id === t);
    if (r !== -1 && this.comments[r])
      return (
        (this.comments[r].content = e),
        i && (this.comments[r].images = i),
        (this.comments = [...this.comments]),
        !0
      );
    for (let s = 0; s < this.comments.length; s++) {
      const o = this.comments[s];
      if (o != null && o.replies) {
        const n = o.replies.findIndex((d) => d.id === t);
        if (n !== -1 && o.replies[n])
          return (
            (o.replies[n].content = e),
            i && (o.replies[n].images = i),
            (this.comments = [...this.comments]),
            !0
          );
      }
    }
    return console.error("수정할 댓글/답글을 찾을 수 없습니다:", t), !1;
  }
  removeComment(t) {
    const e = this.comments.findIndex((i) => i.id === t);
    if (e !== -1)
      return (
        this.comments.splice(e, 1), (this.comments = [...this.comments]), !0
      );
    for (let i = 0; i < this.comments.length; i++) {
      const r = this.comments[i];
      if (r != null && r.replies) {
        const s = r.replies.findIndex((o) => o.id === t);
        if (s !== -1)
          return (
            r.replies.splice(s, 1), (this.comments = [...this.comments]), !0
          );
      }
    }
    return console.error("삭제할 댓글/답글을 찾을 수 없습니다:", t), !1;
  }
  getComments() {
    return [...this.comments];
  }
  getComment(t) {
    return this.comments.find((e) => e.id === t) || null;
  }
  setComments(t) {
    (this.comments = [...t]),
      console.log("전체 댓글 데이터가 업데이트되었습니다:", this.comments);
  }
  clearComments() {
    (this.comments = []), console.log("댓글 데이터가 초기화되었습니다.");
  }
  handleFilterChange(t) {
    const i = t.target.value;
    this.dispatchEvent(
      new CustomEvent("filter-changed", {
        detail: { filterValue: i },
        bubbles: !0,
        composed: !0,
      })
    );
  }
  render() {
    return f`
      <div id="review-viewer">
        <select id="review-filter" @change=${(t) => this.handleFilterChange(t)}>
          <option value="">전체</option>
          <option value="후기">후기</option>
          <option value="질문">질문/답변</option>
        </select>

        <div id="review-content">
          ${this.comments.map((t) => {
            var e, i, r, s;
            return f`
            <div class="comment">
              <div class="comment-body">
                <div class="profile-img">
                  <img src="https://placehold.co/160" alt="profile" />
                </div>
                <div class="comment-container">
                  <div class="comment-top">
                    ${t.author} <time class="reg">${this.formatDate(
              t.createdAt
            )}</time>
                  </div>
                  ${
                    t.images && t.images.length > 0
                      ? f`
                    <div class="comment-images">
                      ${t.images.map(
                        (o, n) => f`
                        <img 
                          src=${o} 
                          alt="댓글 이미지" 
                          class="comment-image" 
                          @click=${(d) =>
                            this.handleImageClick(
                              d,
                              o,
                              `댓글 이미지 ${n + 1}`,
                              t.id,
                              n
                            )}
                        />
                      `
                      )}
                    </div>
                  `
                      : ""
                  }
                                          <div class="comment-content">
                          ${
                            this.editingCommentId !== t.id
                              ? f`
                            ${
                              t.type === "question"
                                ? f`<span class="question-badge">질문</span>`
                                : ""
                            }
                            ${
                              t.type === "review"
                                ? f`<span class="review-badge">후기</span>`
                                : ""
                            }
                            ${
                              t.type === "answer"
                                ? f`<span class="answer-badge">답변</span>`
                                : ""
                            }
                          `
                              : ""
                          }
                          ${
                            this.editingCommentId === t.id
                              ? f`
                      <div class="edit-form">
                        <div class="reply-toolbar">
                          <button 
                            @click=${() => this.addEditImages(t.id)}
                            class="image-upload-btn"
                          >
                            📷 이미지 추가
                          </button>
                          <div class="button-group">
                            <button @click=${() =>
                              this.submitEdit(
                                t.id
                              )} class="reply-submit-btn">저장</button>
                            <button @click=${() =>
                              this.cancelEdit(
                                t.id
                              )} class="reply-cancel-btn">취소</button>
                          </div>
                        </div>
                        
                        ${
                          t.images && t.images.length > 0
                            ? f`
                          <div class="comment-images">
                            ${t.images.map(
                              (o, n) => f`
                              <img 
                                src=${o} 
                                alt="기존 이미지" 
                                class="comment-image" 
                                @click=${(d) =>
                                  this.handleImageClick(
                                    d,
                                    o,
                                    `댓글 이미지 ${n + 1}`,
                                    t.id,
                                    n
                                  )}
                              />
                            `
                            )}
                          </div>
                        `
                            : ""
                        }
                        
                        ${
                          (e = this.editImageFiles.get(t.id)) != null &&
                          e.length
                            ? f`
                          <div class="image-preview">
                            ${
                              (i = this.editImageFiles.get(t.id)) == null
                                ? void 0
                                : i.map(
                                    (o, n) => f`
                              <div class="image-preview-item">
                                <img src=${URL.createObjectURL(
                                  o
                                )} alt="새 이미지 미리보기" />
                                <button 
                                  class="remove-btn" 
                                  @click=${() => this.removeEditImage(t.id, n)}
                                  title="이미지 제거"
                                >×</button>
                              </div>
                            `
                                  )
                            }
                          </div>
                        `
                            : ""
                        }
                        
                        <textarea 
                          id="edit-editor-${t.id}" 
                          class="text-editor" 
                          placeholder="내용을 수정하세요..."
                          rows="4"
                        >${t.content}</textarea>
                      </div>
                    `
                              : f`
                    <span class="comment-text">${t.content}</span>
                    `
                          }
                  </div>
                  <div class="comment-bottom">
                    ${
                      this.editingCommentId !== t.id
                        ? f`
                      <div class="comment-bl">
                        <div class="comment-reply-btn" @click=${() =>
                          this.requestReply(t.id)}>답글</div>
                      </div>
                    `
                        : ""
                    }
                    ${
                      this.isAuthor(t) && this.editingCommentId !== t.id
                        ? f`
                      <div class="comment-br">
                        <button class="edit-btn" @click=${() =>
                          this.editComment(t.id)}>수정</button>
                        <button class="delete-btn" @click=${() =>
                          this.deleteComment(t.id)}>삭제</button>
                      </div>
                    `
                        : ""
                    }
                  </div>
                </div>
              </div>

              ${
                t.replies.length > 0 || this.replyOpenMap.get(t.id)
                  ? f`
                <div class="comment-sub">
                  ${t.replies.map((o) => {
                    var n, d;
                    return f`
                    <div class="comment-body">
                      <span class="material-symbols-outlined reply">${De}</span>
                      <div class="profile-img">
                        <img src="https://placehold.co/400" alt="profile" />
                      </div>
                      <div class="comment-container">
                        <div class="comment-top">
                          ${o.author} <time class="reg">${this.formatDate(
                      o.createdAt
                    )}</time>
                        </div>
                        ${
                          o.images && o.images.length > 0
                            ? f`
                          <div class="comment-images">
                            ${o.images.map(
                              (a, c) => f`
                              <img 
                                src=${a} 
                                alt="답글 이미지" 
                                class="comment-image" 
                                @click=${(p) =>
                                  this.handleImageClick(
                                    p,
                                    a,
                                    `답글 이미지 ${c + 1}`,
                                    o.id,
                                    c,
                                    !0
                                  )}
                              />
                            `
                            )}
                          </div>
                        `
                            : ""
                        }
                                                <div class="comment-content">
                          ${
                            this.editingReplyId !== o.id
                              ? f`
                            ${
                              o.type === "question"
                                ? f`<span class="question-badge">질문</span>`
                                : ""
                            }
                            ${
                              o.type === "review"
                                ? f`<span class="review-badge">후기</span>`
                                : ""
                            }
                            ${
                              o.type === "answer"
                                ? f`<span class="answer-badge">답변</span>`
                                : ""
                            }
                          `
                              : ""
                          }
                          ${
                            this.editingReplyId === o.id
                              ? f`
                            <div class="edit-form">
                              <div class="reply-toolbar">
                                <button 
                                  @click=${() => this.addEditImages(o.id)}
                                  class="image-upload-btn"
                                >
                                  📷 이미지 추가
                                </button>
                                <div class="button-group">
                                  <button @click=${() =>
                                    this.submitEdit(
                                      o.id
                                    )} class="reply-submit-btn">저장</button>
                                  <button @click=${() =>
                                    this.cancelEdit(
                                      o.id
                                    )} class="reply-cancel-btn">취소</button>
                                </div>
                              </div>
                              
                              ${
                                o.images && o.images.length > 0
                                  ? f`
                                <div class="comment-images">
                                  ${o.images.map(
                                    (a, c) => f`
                                    <img 
                                      src=${a} 
                                      alt="기존 이미지" 
                                      class="comment-image" 
                                      @click=${(p) =>
                                        this.handleImageClick(
                                          p,
                                          a,
                                          `답글 이미지 ${c + 1}`,
                                          o.id,
                                          c,
                                          !0
                                        )}
                                    />
                                  `
                                  )}
                                </div>
                              `
                                  : ""
                              }
                              
                              ${
                                (n = this.editImageFiles.get(o.id)) != null &&
                                n.length
                                  ? f`
                                <div class="image-preview">
                                  ${
                                    (d = this.editImageFiles.get(o.id)) == null
                                      ? void 0
                                      : d.map(
                                          (a, c) => f`
                                    <div class="image-preview-item">
                                      <img src=${URL.createObjectURL(
                                        a
                                      )} alt="새 이미지 미리보기" />
                                      <button 
                                        class="remove-btn" 
                                        @click=${() =>
                                          this.removeEditImage(o.id, c)}
                                        title="이미지 제거"
                                      >×</button>
                                    </div>
                                  `
                                        )
                                  }
                                </div>
                              `
                                  : ""
                              }
                              
                              <textarea 
                                id="edit-editor-${o.id}" 
                                class="text-editor" 
                                placeholder="내용을 수정하세요..."
                                rows="4"
                              >${o.content}</textarea>
                            </div>
                          `
                              : f`
                          <span class="comment-text">${o.content}</span>
                          `
                          }
                        </div>
                        <div class="comment-bottom">
                          <div class="comment-bl"></div>
                          ${
                            this.isAuthor(o) && this.editingReplyId !== o.id
                              ? f`
                            <div class="comment-br">
                              <button class="edit-btn" @click=${() =>
                                this.editReply(o.id)}>수정</button>
                              <button class="delete-btn" @click=${() =>
                                this.deleteComment(o.id)}>삭제</button>
                            </div>
                          `
                              : ""
                          }
                        </div>
                      </div>
                    </div>
                  `;
                  })}

                  ${
                    this.replyOpenMap.get(t.id)
                      ? f`
                    <div class="comment-body reply-form">
                      <span class="material-symbols-outlined reply">${De}</span>
                      <div class="profile-img">
                        <img src="https://placehold.co/400" alt="profile" />
                      </div>
                      <div class="comment-reply-container">
                        <div class="reply-toolbar">
                          <button 
                            @click=${() => this.addImages(t.id)}
                            class="image-upload-btn"
                          >
                            📷 이미지 추가
                          </button>
                          <div class="button-group">
                            <button @click=${() =>
                              this.submitReply(
                                t.id
                              )} class="reply-submit-btn">등록</button>
                            <button @click=${() =>
                              this.cancelReply(
                                t.id
                              )} class="reply-cancel-btn">취소</button>
                          </div>
                        </div>
                        
                        ${
                          (r = this.imageFiles.get(t.id)) != null && r.length
                            ? f`
                          <div class="image-preview">
                            ${
                              (s = this.imageFiles.get(t.id)) == null
                                ? void 0
                                : s.map(
                                    (o, n) => f`
                              <div class="image-preview-item">
                                <img src=${URL.createObjectURL(
                                  o
                                )} alt="미리보기" />
                                <button 
                                  class="remove-btn" 
                                  @click=${() => this.removeImage(t.id, n)}
                                  title="이미지 제거"
                                >×</button>
                              </div>
                            `
                                  )
                            }
                          </div>
                        `
                            : ""
                        }
                        
                        <textarea 
                          id="editor-${t.id}" 
                          class="text-editor" 
                          placeholder="답글을 입력하세요..."
                          rows="4"
                        ></textarea>
                      </div>
                    </div>
                  `
                      : ""
                  }
                </div>
              `
                  : ""
              }
            </div>
          `;
          })}
        </div>
      </div>
    `;
  }
};
P.styles = U`
        #review-viewer {
            display: flex;
            flex-direction: column;
            padding: var(--size-2) 0;
            #review-filter {
                all: revert;
                width: fit-content;
                padding: var(--size-2) var(--size-1);
                margin-bottom: var(--size-2);
                outline: none;
                border-radius: var(--radius-3);
            }
            .comment {
                display: flex;
                flex-direction: column;
                padding: var(--size-2);
                gap: var(--size-2);
                border-top: var(--border-size-1) solid var(--surface-2);
                .comment-container {
                    display: flex;
                    flex-direction: column;
                    flex: 1;
                }
                .comment-reply-container {
                    display: flex;
                    flex-direction: column;
                    flex: 1;
                    gap: var(--size-2);
                }
                .comment-top {
                    font-weight: var(--font-weight-7);
                    .reg {
                        font-weight: var(--font-weight-5);
                        color: var(--text-2);
                        font-size: var(--font-size-0);
                        margin-left: var(--size-2);
                    }
                }
                .comment-body {
                    display: flex;
                    flex-direction: row;
                    padding: var(--size-2) 0;
                    gap: var(--size-2);
                    .profile-img img {
                        min-width: var(--size-8);
                        height: var(--size-8);
                        background-color: #fff;
                        border-radius: var(--radius-round);
                        user-select: none;
                        -webkit-user-select: none;
                        -webkit-user-drag: none;
                        margin-top: var(--size-2);
                    }
                    .question-badge {
                        border: var(--border-size-1) solid var(--lime-6);
                        border-radius: var(--radius-2);
                        padding: 0 var(--size-1);
                        user-select: none;
                        background-color: var(--lime-9);
                        font-size: var(--font-size-0);
                        color: var(--lime-1);
                    }
                    .review-badge {
                        border: var(--border-size-1) solid var(--blue-6);
                        border-radius: var(--radius-2);
                        padding: 0 var(--size-1);
                        user-select: none;
                        background-color: var(--blue-9);
                        font-size: var(--font-size-0);
                        color: var(--blue-1);
                    }
                    .answer-badge {
                        border: var(--border-size-1) solid var(--orange-6);
                        border-radius: var(--radius-2);
                        padding: 0 var(--size-1);
                        user-select: none;
                        background-color: var(--orange-9);
                        font-size: var(--font-size-0);
                        color: var(--orange-1);
                    }
                    .comment-text{
                        white-space: pre-line;
                        font-size: var(--font-size-1);
                    }
                    .comment-images {
                        display: flex;
                        flex-wrap: wrap;
                        gap: var(--size-2);
                        margin: var(--size-2) 0;
                    }
                    .comment-image {
                        max-width: 200px;
                        max-height: 200px;
                        border-radius: var(--radius-2);
                        object-fit: cover;
                        cursor: pointer;
                        transition: transform 0.2s ease;
                    }
                    .comment-image:hover {
                        transform: scale(1.05);
                    }
                }
                .comment-bottom {
                    display: flex;
                    flex-direction: row;
                    justify-content: space-between;
                    padding-top: var(--size-2);
                    .comment-reply-btn {
                        cursor: pointer;
                        user-select: none;
                        -webkit-user-select: none;
                        &:hover {
                            text-decoration: underline;
                        }
                    }
                    .edit-btn, .delete-btn {
                        cursor: pointer;
                        user-select: none;
                        -webkit-user-select: none;
                        margin-left: var(--size-2);
                        padding: var(--size-1) var(--size-2);
                        border-radius: var(--radius-2);
                        font-size: var(--font-size-0);
                        transition: background-color 0.2s ease;
                        border: none;
                        background: transparent;
                    }
                    .edit-btn {
                        color: var(--blue-6);
                        &:hover {
                            background-color: var(--blue-9);
                        }
                    }
                    .delete-btn {
                        color: var(--red-6);
                        &:hover {
                            background-color: var(--red-9);
                        }
                    }
                }

                .comment-sub {
                    display: flex;
                    flex-direction: column;
                    .reply {
                        padding-top: var(--size-4);
                    }
                    .comment-body {
                        margin-left: var(--size-5);
                        border-top: var(--border-size-1) solid var(--surface-3);
                    }
                }
            }
        }

        /* 텍스트 에디터 스타일 */
        .text-editor {
            background: white;
            border: 1px solid #ccc;
            border-radius: 4px;
            min-height: 100px;
            padding: 12px;
            font-size: 14px;
            line-height: 1.5;
            color: #333;
            outline: none;
            resize: vertical;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
            width: 100%;
            box-sizing: border-box;
        }

        .text-editor:focus {
            border-color: #0066cc;
            box-shadow: 0 0 0 2px rgba(0, 102, 204, 0.2);
        }

        .editor-toolbar {
            display: flex;
            gap: 8px;
            align-items: center;
        }

        .image-upload-btn {
            background: var(--blue-6);
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 12px;
            display: flex;
            align-items: center;
            gap: 4px;
            transition: background-color 0.2s ease;
        }

        .image-upload-btn:hover {
            background: var(--blue-7);
        }

        .image-preview {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
            margin-top: 8px;
        }

        .image-preview-item {
            position: relative;
            border: 1px solid #ccc;
            border-radius: 4px;
            overflow: hidden;
            max-width: 120px;
        }

        .image-preview-item img {
            width: 100%;
            height: 80px;
            object-fit: cover;
            display: block;
        }

        .image-preview-item .remove-btn {
            position: absolute;
            top: 4px;
            right: 4px;
            background: rgba(255, 0, 0, 0.8);
            color: white;
            border: none;
            border-radius: 50%;
            width: 20px;
            height: 20px;
            cursor: pointer;
            font-size: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .image-preview-item .remove-btn:hover {
            background: rgba(255, 0, 0, 1);
        }

        .upload-progress {
            margin-top: 8px;
            font-size: 12px;
            color: #666;
        }

        /* 답변 작성 툴바 스타일 */
        .reply-toolbar {
            display: flex;
            align-items: center;
            gap: var(--size-2);
        }

        .reply-toolbar .button-group {
            margin-left: auto;
            display: flex;
            gap: var(--size-2);
        }

        .reply-submit-btn {
            padding: var(--size-2) var(--size-3);
            background: var(--surface-2);
            color: white;
            border: none;
            border-radius: var(--radius-2);
            cursor: pointer;
            font-size: var(--font-size-0);
            transition: background-color 0.2s ease;
        }

        .reply-submit-btn:hover {
            background: var(--surface-3);
        }

        .reply-cancel-btn {
            padding: var(--size-2) var(--size-3);
            background: var(--red-7);
            color: white;
            border: none;
            border-radius: var(--radius-2);
            cursor: pointer;
            font-size: var(--font-size-0);
            transition: background-color 0.2s ease;
        }

        .reply-cancel-btn:hover {
            background: var(--red-8);
        }

        .edit-form {
          display: flex;
          flex-direction: column;
          gap: var(--size-2);
        }
    `;
L([x({ type: Array })], P.prototype, "comments", 2);
L([T()], P.prototype, "replyOpenMap", 2);
L([T()], P.prototype, "imageFiles", 2);
L([x({ type: String })], P.prototype, "currentUserId", 2);
L([T()], P.prototype, "editingCommentId", 2);
L([T()], P.prototype, "editingReplyId", 2);
L([T()], P.prototype, "editImageFiles", 2);
P = L([j("review-viewer")], P);
var Vt = Object.defineProperty,
  Zt = Object.getOwnPropertyDescriptor,
  ne = (t, e, i, r) => {
    for (
      var s = r > 1 ? void 0 : r ? Zt(e, i) : e, o = t.length - 1, n;
      o >= 0;
      o--
    )
      (n = t[o]) && (s = (r ? n(e, i, s) : n(s)) || s);
    return r && s && Vt(e, i, s), s;
  };
let V = class extends C {
  constructor() {
    super(...arguments),
      (this.author = "현재 사용자"),
      (this.userId = 1),
      (this.selectedImages = []),
      (this.selectedType = null);
  }
  handleTypeChange(t) {
    const e = t.target;
    this.selectedType = e.id === "re" ? "review" : "question";
  }
  handleImageUpload() {
    if (!this.selectedType) {
      alert("후기 또는 질문을 먼저 선택해주세요.");
      return;
    }
    const t = document.createElement("input");
    (t.type = "file"),
      (t.accept = "image/*"),
      (t.multiple = !0),
      (t.onchange = (e) => {
        const i = Array.from(e.target.files || []);
        (this.selectedImages = [...this.selectedImages, ...i]),
          this.requestUpdate();
      }),
      t.click();
  }
  removeImage(t) {
    this.selectedImages.splice(t, 1),
      (this.selectedImages = [...this.selectedImages]),
      this.requestUpdate();
  }
  handleSubmit() {
    const t = this.renderRoot.querySelector("#review-textarea"),
      e = t.value.trim();
    if (!this.selectedType) {
      alert("후기 또는 질문을 선택해주세요.");
      return;
    }
    if (!e && this.selectedImages.length === 0) {
      alert("내용을 입력하거나 이미지를 추가해주세요.");
      return;
    }
    const i = this.selectedImages.map((s) => URL.createObjectURL(s));
    this.dispatchEvent(
      new CustomEvent("review-submitted", {
        detail: {
          type: this.selectedType,
          content: e,
          images: this.selectedImages,
          imageUrls: i,
          author: this.author,
          userId: this.userId,
        },
        bubbles: !0,
        composed: !0,
      })
    ),
      (t.value = ""),
      (this.selectedImages = []),
      (this.selectedType = null),
      this.renderRoot
        .querySelectorAll('input[type="radio"]')
        .forEach((s) => (s.checked = !1)),
      this.requestUpdate();
  }
  render() {
    return f`
            <div id="review-select">
                <input type="radio" name="review-tab" id="re" @change=${
                  this.handleTypeChange
                }>
                <label for="re">후기</label>
                <input type="radio" name="review-tab" id="qu" @change=${
                  this.handleTypeChange
                }>
                <label for="qu">질문</label>
                <button 
                    type="button" 
                    id="image-upload-btn"
                    @click=${this.handleImageUpload}
                    ?disabled=${!this.selectedType}
                >
                    📷 이미지 추가
                </button>
                <button 
                    style="margin-left: auto;"
                    id="submit-btn" 
                    @click=${this.handleSubmit}
                    ?disabled=${!this.selectedType}
                >
                    올리기
                </button>
            </div>
            
            <textarea 
                id="review-textarea" 
                placeholder="후기와 질문 중 하나를 선택해주세요."
                ?disabled=${!this.selectedType}
            ></textarea>
            
            ${
              this.selectedImages.length > 0
                ? f`
                <div id="image-preview">
                    ${this.selectedImages.map(
                      (t, e) => f`
                        <div class="image-preview-item">
                            <img src=${URL.createObjectURL(t)} alt="미리보기" />
                            <button 
                                class="remove-btn" 
                                @click=${() => this.removeImage(e)}
                                title="이미지 제거"
                            >×</button>
                        </div>
                    `
                    )}
                </div>
            `
                : ""
            }
            
            <div id="submit-section">
            </div>
        `;
  }
};
V.styles = U`
        :host {
            display: flex;
            flex-direction: column;
            gap: var(--size-2);
            padding-bottom: var(--size-2);
            border-bottom: var(--border-size-1) solid var(--surface-2);
        }

        #review-select {
            display: flex;
            flex-direction: row;
            gap: var(--size-2);
            align-items: center;
        }

        #review-select label {
            height: fit-content;
            text-align: center;
            padding: var(--size-2) var(--size-3);
            border: var(--border-size-1) solid var(--surface-3);
            border-radius: var(--radius-2);
            font-size: var(--font-size-0);
            cursor: pointer;
            transition: background-color 0.2s ease;
        }

        #review-select input[type="radio"] {
            display: none;
        }

        #review-select input[type="radio"]:checked + label {
            background-color: var(--surface-3);
        }

        #image-upload-btn {
            all: revert;
            outline: none;
            background-color: var(--blue-6);
            color: white;
            padding: var(--size-2) var(--size-3);
            border: none;
            border-radius: var(--radius-2);
            cursor: pointer;
            font-size: var(--font-size-0);
            transition: background-color 0.2s ease;
        }

        #image-upload-btn:hover:not(:disabled) {
            background-color: var(--blue-7);
        }

        #image-upload-btn:disabled {
            background-color: var(--surface-2);
            color: var(--text-2);
            cursor: default;
        }

        textarea {
            all: revert;
            flex: 1;
            min-height: var(--size-9);
            resize: none;
            outline: none;
            border: none;
            padding: var(--size-3);
            font-family: "Noto Sans KR", sans-serif;
            color: #000;
            background-color: #fff;
            caret-color: auto;
            border-radius: var(--radius-3);
            transition: background-color 0.2s ease;
        }

        textarea:disabled {
            background-color: var(--surface-2);
            cursor: default;
        }

        #image-preview {
            display: flex;
            flex-wrap: wrap;
            gap: var(--size-2);
        }

        .image-preview-item {
            position: relative;
            border: 1px solid var(--surface-3);
            border-radius: var(--radius-2);
            overflow: hidden;
            max-width: 120px;
        }

        .image-preview-item img {
            width: 100%;
            height: 80px;
            object-fit: cover;
            display: block;
        }

        .image-preview-item .remove-btn {
            position: absolute;
            top: 4px;
            right: 4px;
            background: rgba(255, 0, 0, 0.8);
            color: white;
            border: none;
            border-radius: 50%;
            width: 20px;
            height: 20px;
            cursor: pointer;
            font-size: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .image-preview-item .remove-btn:hover {
            background: rgba(255, 0, 0, 1);
        }

        #submit-section {
            display: flex;
            justify-content: flex-end;
        }

        #submit-btn {
            all: revert;
            outline: none;
            background-color: var(--surface-2);
            padding: var(--size-2) var(--size-3);
            border: none;
            border-radius: var(--radius-2);
            cursor: pointer;
            transition: background-color 0.2s ease;
        }

        #submit-btn:hover:not(:disabled) {
            background-color: var(--surface-3);
        }

        #submit-btn:disabled {
            opacity: 0.5;
            cursor: default
        }
    `;
ne([x({ type: String })], V.prototype, "author", 2);
ne([x({ type: Number })], V.prototype, "userId", 2);
ne([T()], V.prototype, "selectedImages", 2);
ne([T()], V.prototype, "selectedType", 2);
V = ne([j("review-input")], V);
