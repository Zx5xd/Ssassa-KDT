(function(){const e=document.createElement("link").relList;if(e&&e.supports&&e.supports("modulepreload"))return;for(const i of document.querySelectorAll('link[rel="modulepreload"]'))r(i);new MutationObserver(i=>{for(const o of i)if(o.type==="childList")for(const n of o.addedNodes)n.tagName==="LINK"&&n.rel==="modulepreload"&&r(n)}).observe(document,{childList:!0,subtree:!0});function t(i){const o={};return i.integrity&&(o.integrity=i.integrity),i.referrerPolicy&&(o.referrerPolicy=i.referrerPolicy),i.crossOrigin==="use-credentials"?o.credentials="include":i.crossOrigin==="anonymous"?o.credentials="omit":o.credentials="same-origin",o}function r(i){if(i.ep)return;i.ep=!0;const o=t(i);fetch(i.href,o)}})();/**
 * @license
 * Copyright 2019 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const se=globalThis,fe=se.ShadowRoot&&(se.ShadyCSS===void 0||se.ShadyCSS.nativeShadow)&&"adoptedStyleSheets"in Document.prototype&&"replace"in CSSStyleSheet.prototype,ve=Symbol(),be=new WeakMap;let je=class{constructor(e,t,r){if(this._$cssResult$=!0,r!==ve)throw Error("CSSResult is not constructable. Use `unsafeCSS` or `css` instead.");this.cssText=e,this.t=t}get styleSheet(){let e=this.o;const t=this.t;if(fe&&e===void 0){const r=t!==void 0&&t.length===1;r&&(e=be.get(t)),e===void 0&&((this.o=e=new CSSStyleSheet).replaceSync(this.cssText),r&&be.set(t,e))}return e}toString(){return this.cssText}};const Ve=s=>new je(typeof s=="string"?s:s+"",void 0,ve),q=(s,...e)=>{const t=s.length===1?s[0]:e.reduce((r,i,o)=>r+(n=>{if(n._$cssResult$===!0)return n.cssText;if(typeof n=="number")return n;throw Error("Value passed to 'css' function must be a 'css' function result: "+n+". Use 'unsafeCSS' to pass non-literal values, but take care to ensure page security.")})(i)+s[o+1],s[0]);return new je(t,s,ve)},We=(s,e)=>{if(fe)s.adoptedStyleSheets=e.map(t=>t instanceof CSSStyleSheet?t:t.styleSheet);else for(const t of e){const r=document.createElement("style"),i=se.litNonce;i!==void 0&&r.setAttribute("nonce",i),r.textContent=t.cssText,s.appendChild(r)}},_e=fe?s=>s:s=>s instanceof CSSStyleSheet?(e=>{let t="";for(const r of e.cssRules)t+=r.cssText;return Ve(t)})(s):s;/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const{is:Je,defineProperty:Fe,getOwnPropertyDescriptor:Ke,getOwnPropertyNames:Ge,getOwnPropertySymbols:Qe,getPrototypeOf:Ye}=Object,U=globalThis,we=U.trustedTypes,Xe=we?we.emptyScript:"",le=U.reactiveElementPolyfillSupport,F=(s,e)=>s,ie={toAttribute(s,e){switch(e){case Boolean:s=s?Xe:null;break;case Object:case Array:s=s==null?s:JSON.stringify(s)}return s},fromAttribute(s,e){let t=s;switch(e){case Boolean:t=s!==null;break;case Number:t=s===null?null:Number(s);break;case Object:case Array:try{t=JSON.parse(s)}catch{t=null}}return t}},me=(s,e)=>!Je(s,e),xe={attribute:!0,type:String,converter:ie,reflect:!1,useDefault:!1,hasChanged:me};Symbol.metadata??(Symbol.metadata=Symbol("metadata")),U.litPropertyMetadata??(U.litPropertyMetadata=new WeakMap);let B=class extends HTMLElement{static addInitializer(e){this._$Ei(),(this.l??(this.l=[])).push(e)}static get observedAttributes(){return this.finalize(),this._$Eh&&[...this._$Eh.keys()]}static createProperty(e,t=xe){if(t.state&&(t.attribute=!1),this._$Ei(),this.prototype.hasOwnProperty(e)&&((t=Object.create(t)).wrapped=!0),this.elementProperties.set(e,t),!t.noAccessor){const r=Symbol(),i=this.getPropertyDescriptor(e,r,t);i!==void 0&&Fe(this.prototype,e,i)}}static getPropertyDescriptor(e,t,r){const{get:i,set:o}=Ke(this.prototype,e)??{get(){return this[t]},set(n){this[t]=n}};return{get:i,set(n){const d=i==null?void 0:i.call(this);o==null||o.call(this,n),this.requestUpdate(e,d,r)},configurable:!0,enumerable:!0}}static getPropertyOptions(e){return this.elementProperties.get(e)??xe}static _$Ei(){if(this.hasOwnProperty(F("elementProperties")))return;const e=Ye(this);e.finalize(),e.l!==void 0&&(this.l=[...e.l]),this.elementProperties=new Map(e.elementProperties)}static finalize(){if(this.hasOwnProperty(F("finalized")))return;if(this.finalized=!0,this._$Ei(),this.hasOwnProperty(F("properties"))){const t=this.properties,r=[...Ge(t),...Qe(t)];for(const i of r)this.createProperty(i,t[i])}const e=this[Symbol.metadata];if(e!==null){const t=litPropertyMetadata.get(e);if(t!==void 0)for(const[r,i]of t)this.elementProperties.set(r,i)}this._$Eh=new Map;for(const[t,r]of this.elementProperties){const i=this._$Eu(t,r);i!==void 0&&this._$Eh.set(i,t)}this.elementStyles=this.finalizeStyles(this.styles)}static finalizeStyles(e){const t=[];if(Array.isArray(e)){const r=new Set(e.flat(1/0).reverse());for(const i of r)t.unshift(_e(i))}else e!==void 0&&t.push(_e(e));return t}static _$Eu(e,t){const r=t.attribute;return r===!1?void 0:typeof r=="string"?r:typeof e=="string"?e.toLowerCase():void 0}constructor(){super(),this._$Ep=void 0,this.isUpdatePending=!1,this.hasUpdated=!1,this._$Em=null,this._$Ev()}_$Ev(){var e;this._$ES=new Promise(t=>this.enableUpdating=t),this._$AL=new Map,this._$E_(),this.requestUpdate(),(e=this.constructor.l)==null||e.forEach(t=>t(this))}addController(e){var t;(this._$EO??(this._$EO=new Set)).add(e),this.renderRoot!==void 0&&this.isConnected&&((t=e.hostConnected)==null||t.call(e))}removeController(e){var t;(t=this._$EO)==null||t.delete(e)}_$E_(){const e=new Map,t=this.constructor.elementProperties;for(const r of t.keys())this.hasOwnProperty(r)&&(e.set(r,this[r]),delete this[r]);e.size>0&&(this._$Ep=e)}createRenderRoot(){const e=this.shadowRoot??this.attachShadow(this.constructor.shadowRootOptions);return We(e,this.constructor.elementStyles),e}connectedCallback(){var e;this.renderRoot??(this.renderRoot=this.createRenderRoot()),this.enableUpdating(!0),(e=this._$EO)==null||e.forEach(t=>{var r;return(r=t.hostConnected)==null?void 0:r.call(t)})}enableUpdating(e){}disconnectedCallback(){var e;(e=this._$EO)==null||e.forEach(t=>{var r;return(r=t.hostDisconnected)==null?void 0:r.call(t)})}attributeChangedCallback(e,t,r){this._$AK(e,r)}_$ET(e,t){var o;const r=this.constructor.elementProperties.get(e),i=this.constructor._$Eu(e,r);if(i!==void 0&&r.reflect===!0){const n=(((o=r.converter)==null?void 0:o.toAttribute)!==void 0?r.converter:ie).toAttribute(t,r.type);this._$Em=e,n==null?this.removeAttribute(i):this.setAttribute(i,n),this._$Em=null}}_$AK(e,t){var o,n;const r=this.constructor,i=r._$Eh.get(e);if(i!==void 0&&this._$Em!==i){const d=r.getPropertyOptions(i),a=typeof d.converter=="function"?{fromAttribute:d.converter}:((o=d.converter)==null?void 0:o.fromAttribute)!==void 0?d.converter:ie;this._$Em=i,this[i]=a.fromAttribute(t,d.type)??((n=this._$Ej)==null?void 0:n.get(i))??null,this._$Em=null}}requestUpdate(e,t,r){var i;if(e!==void 0){const o=this.constructor,n=this[e];if(r??(r=o.getPropertyOptions(e)),!((r.hasChanged??me)(n,t)||r.useDefault&&r.reflect&&n===((i=this._$Ej)==null?void 0:i.get(e))&&!this.hasAttribute(o._$Eu(e,r))))return;this.C(e,t,r)}this.isUpdatePending===!1&&(this._$ES=this._$EP())}C(e,t,{useDefault:r,reflect:i,wrapped:o},n){r&&!(this._$Ej??(this._$Ej=new Map)).has(e)&&(this._$Ej.set(e,n??t??this[e]),o!==!0||n!==void 0)||(this._$AL.has(e)||(this.hasUpdated||r||(t=void 0),this._$AL.set(e,t)),i===!0&&this._$Em!==e&&(this._$Eq??(this._$Eq=new Set)).add(e))}async _$EP(){this.isUpdatePending=!0;try{await this._$ES}catch(t){Promise.reject(t)}const e=this.scheduleUpdate();return e!=null&&await e,!this.isUpdatePending}scheduleUpdate(){return this.performUpdate()}performUpdate(){var r;if(!this.isUpdatePending)return;if(!this.hasUpdated){if(this.renderRoot??(this.renderRoot=this.createRenderRoot()),this._$Ep){for(const[o,n]of this._$Ep)this[o]=n;this._$Ep=void 0}const i=this.constructor.elementProperties;if(i.size>0)for(const[o,n]of i){const{wrapped:d}=n,a=this[o];d!==!0||this._$AL.has(o)||a===void 0||this.C(o,void 0,n,a)}}let e=!1;const t=this._$AL;try{e=this.shouldUpdate(t),e?(this.willUpdate(t),(r=this._$EO)==null||r.forEach(i=>{var o;return(o=i.hostUpdate)==null?void 0:o.call(i)}),this.update(t)):this._$EM()}catch(i){throw e=!1,this._$EM(),i}e&&this._$AE(t)}willUpdate(e){}_$AE(e){var t;(t=this._$EO)==null||t.forEach(r=>{var i;return(i=r.hostUpdated)==null?void 0:i.call(r)}),this.hasUpdated||(this.hasUpdated=!0,this.firstUpdated(e)),this.updated(e)}_$EM(){this._$AL=new Map,this.isUpdatePending=!1}get updateComplete(){return this.getUpdateComplete()}getUpdateComplete(){return this._$ES}shouldUpdate(e){return!0}update(e){this._$Eq&&(this._$Eq=this._$Eq.forEach(t=>this._$ET(t,this[t]))),this._$EM()}updated(e){}firstUpdated(e){}};B.elementStyles=[],B.shadowRootOptions={mode:"open"},B[F("elementProperties")]=new Map,B[F("finalized")]=new Map,le==null||le({ReactiveElement:B}),(U.reactiveElementVersions??(U.reactiveElementVersions=[])).push("2.1.0");/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const K=globalThis,re=K.trustedTypes,Ae=re?re.createPolicy("lit-html",{createHTML:s=>s}):void 0,Re="$lit$",z=`lit$${Math.random().toFixed(9).slice(2)}$`,Te="?"+z,et=`<${Te}>`,N=document,G=()=>N.createComment(""),Q=s=>s===null||typeof s!="object"&&typeof s!="function",ge=Array.isArray,tt=s=>ge(s)||typeof(s==null?void 0:s[Symbol.iterator])=="function",ce=`[ 	
\f\r]`,W=/<(?:(!--|\/[^a-zA-Z])|(\/?[a-zA-Z][^>\s]*)|(\/?$))/g,Se=/-->/g,Ee=/>/g,I=RegExp(`>|${ce}(?:([^\\s"'>=/]+)(${ce}*=${ce}*(?:[^ 	
\f\r"'\`<>=]|("|')|))|$)`,"g"),ke=/'/g,Pe=/"/g,Ne=/^(?:script|style|textarea|title)$/i,st=s=>(e,...t)=>({_$litType$:s,strings:e,values:t}),A=st(1),M=Symbol.for("lit-noChange"),E=Symbol.for("lit-nothing"),Ce=new WeakMap,R=N.createTreeWalker(N,129);function Le(s,e){if(!ge(s)||!s.hasOwnProperty("raw"))throw Error("invalid template strings array");return Ae!==void 0?Ae.createHTML(e):e}const it=(s,e)=>{const t=s.length-1,r=[];let i,o=e===2?"<svg>":e===3?"<math>":"",n=W;for(let d=0;d<t;d++){const a=s[d];let c,u,l=-1,h=0;for(;h<a.length&&(n.lastIndex=h,u=n.exec(a),u!==null);)h=n.lastIndex,n===W?u[1]==="!--"?n=Se:u[1]!==void 0?n=Ee:u[2]!==void 0?(Ne.test(u[2])&&(i=RegExp("</"+u[2],"g")),n=I):u[3]!==void 0&&(n=I):n===I?u[0]===">"?(n=i??W,l=-1):u[1]===void 0?l=-2:(l=n.lastIndex-u[2].length,c=u[1],n=u[3]===void 0?I:u[3]==='"'?Pe:ke):n===Pe||n===ke?n=I:n===Se||n===Ee?n=W:(n=I,i=void 0);const v=n===I&&s[d+1].startsWith("/>")?" ":"";o+=n===W?a+et:l>=0?(r.push(c),a.slice(0,l)+Re+a.slice(l)+z+v):a+z+(l===-2?d:v)}return[Le(s,o+(s[t]||"<?>")+(e===2?"</svg>":e===3?"</math>":"")),r]};class Y{constructor({strings:e,_$litType$:t},r){let i;this.parts=[];let o=0,n=0;const d=e.length-1,a=this.parts,[c,u]=it(e,t);if(this.el=Y.createElement(c,r),R.currentNode=this.el.content,t===2||t===3){const l=this.el.content.firstChild;l.replaceWith(...l.childNodes)}for(;(i=R.nextNode())!==null&&a.length<d;){if(i.nodeType===1){if(i.hasAttributes())for(const l of i.getAttributeNames())if(l.endsWith(Re)){const h=u[n++],v=i.getAttribute(l).split(z),g=/([.?@])?(.*)/.exec(h);a.push({type:1,index:o,name:g[2],strings:v,ctor:g[1]==="."?ot:g[1]==="?"?nt:g[1]==="@"?at:ne}),i.removeAttribute(l)}else l.startsWith(z)&&(a.push({type:6,index:o}),i.removeAttribute(l));if(Ne.test(i.tagName)){const l=i.textContent.split(z),h=l.length-1;if(h>0){i.textContent=re?re.emptyScript:"";for(let v=0;v<h;v++)i.append(l[v],G()),R.nextNode(),a.push({type:2,index:++o});i.append(l[h],G())}}}else if(i.nodeType===8)if(i.data===Te)a.push({type:2,index:o});else{let l=-1;for(;(l=i.data.indexOf(z,l+1))!==-1;)a.push({type:7,index:o}),l+=z.length-1}o++}}static createElement(e,t){const r=N.createElement("template");return r.innerHTML=e,r}}function H(s,e,t=s,r){var n,d;if(e===M)return e;let i=r!==void 0?(n=t._$Co)==null?void 0:n[r]:t._$Cl;const o=Q(e)?void 0:e._$litDirective$;return(i==null?void 0:i.constructor)!==o&&((d=i==null?void 0:i._$AO)==null||d.call(i,!1),o===void 0?i=void 0:(i=new o(s),i._$AT(s,t,r)),r!==void 0?(t._$Co??(t._$Co=[]))[r]=i:t._$Cl=i),i!==void 0&&(e=H(s,i._$AS(s,e.values),i,r)),e}let rt=class{constructor(e,t){this._$AV=[],this._$AN=void 0,this._$AD=e,this._$AM=t}get parentNode(){return this._$AM.parentNode}get _$AU(){return this._$AM._$AU}u(e){const{el:{content:t},parts:r}=this._$AD,i=((e==null?void 0:e.creationScope)??N).importNode(t,!0);R.currentNode=i;let o=R.nextNode(),n=0,d=0,a=r[0];for(;a!==void 0;){if(n===a.index){let c;a.type===2?c=new Z(o,o.nextSibling,this,e):a.type===1?c=new a.ctor(o,a.name,a.strings,this,e):a.type===6&&(c=new lt(o,this,e)),this._$AV.push(c),a=r[++d]}n!==(a==null?void 0:a.index)&&(o=R.nextNode(),n++)}return R.currentNode=N,i}p(e){let t=0;for(const r of this._$AV)r!==void 0&&(r.strings!==void 0?(r._$AI(e,r,t),t+=r.strings.length-2):r._$AI(e[t])),t++}};class Z{get _$AU(){var e;return((e=this._$AM)==null?void 0:e._$AU)??this._$Cv}constructor(e,t,r,i){this.type=2,this._$AH=E,this._$AN=void 0,this._$AA=e,this._$AB=t,this._$AM=r,this.options=i,this._$Cv=(i==null?void 0:i.isConnected)??!0}get parentNode(){let e=this._$AA.parentNode;const t=this._$AM;return t!==void 0&&(e==null?void 0:e.nodeType)===11&&(e=t.parentNode),e}get startNode(){return this._$AA}get endNode(){return this._$AB}_$AI(e,t=this){e=H(this,e,t),Q(e)?e===E||e==null||e===""?(this._$AH!==E&&this._$AR(),this._$AH=E):e!==this._$AH&&e!==M&&this._(e):e._$litType$!==void 0?this.$(e):e.nodeType!==void 0?this.T(e):tt(e)?this.k(e):this._(e)}O(e){return this._$AA.parentNode.insertBefore(e,this._$AB)}T(e){this._$AH!==e&&(this._$AR(),this._$AH=this.O(e))}_(e){this._$AH!==E&&Q(this._$AH)?this._$AA.nextSibling.data=e:this.T(N.createTextNode(e)),this._$AH=e}$(e){var o;const{values:t,_$litType$:r}=e,i=typeof r=="number"?this._$AC(e):(r.el===void 0&&(r.el=Y.createElement(Le(r.h,r.h[0]),this.options)),r);if(((o=this._$AH)==null?void 0:o._$AD)===i)this._$AH.p(t);else{const n=new rt(i,this),d=n.u(this.options);n.p(t),this.T(d),this._$AH=n}}_$AC(e){let t=Ce.get(e.strings);return t===void 0&&Ce.set(e.strings,t=new Y(e)),t}k(e){ge(this._$AH)||(this._$AH=[],this._$AR());const t=this._$AH;let r,i=0;for(const o of e)i===t.length?t.push(r=new Z(this.O(G()),this.O(G()),this,this.options)):r=t[i],r._$AI(o),i++;i<t.length&&(this._$AR(r&&r._$AB.nextSibling,i),t.length=i)}_$AR(e=this._$AA.nextSibling,t){var r;for((r=this._$AP)==null?void 0:r.call(this,!1,!0,t);e&&e!==this._$AB;){const i=e.nextSibling;e.remove(),e=i}}setConnected(e){var t;this._$AM===void 0&&(this._$Cv=e,(t=this._$AP)==null||t.call(this,e))}}class ne{get tagName(){return this.element.tagName}get _$AU(){return this._$AM._$AU}constructor(e,t,r,i,o){this.type=1,this._$AH=E,this._$AN=void 0,this.element=e,this.name=t,this._$AM=i,this.options=o,r.length>2||r[0]!==""||r[1]!==""?(this._$AH=Array(r.length-1).fill(new String),this.strings=r):this._$AH=E}_$AI(e,t=this,r,i){const o=this.strings;let n=!1;if(o===void 0)e=H(this,e,t,0),n=!Q(e)||e!==this._$AH&&e!==M,n&&(this._$AH=e);else{const d=e;let a,c;for(e=o[0],a=0;a<o.length-1;a++)c=H(this,d[r+a],t,a),c===M&&(c=this._$AH[a]),n||(n=!Q(c)||c!==this._$AH[a]),c===E?e=E:e!==E&&(e+=(c??"")+o[a+1]),this._$AH[a]=c}n&&!i&&this.j(e)}j(e){e===E?this.element.removeAttribute(this.name):this.element.setAttribute(this.name,e??"")}}class ot extends ne{constructor(){super(...arguments),this.type=3}j(e){this.element[this.name]=e===E?void 0:e}}class nt extends ne{constructor(){super(...arguments),this.type=4}j(e){this.element.toggleAttribute(this.name,!!e&&e!==E)}}class at extends ne{constructor(e,t,r,i,o){super(e,t,r,i,o),this.type=5}_$AI(e,t=this){if((e=H(this,e,t,0)??E)===M)return;const r=this._$AH,i=e===E&&r!==E||e.capture!==r.capture||e.once!==r.once||e.passive!==r.passive,o=e!==E&&(r===E||i);i&&this.element.removeEventListener(this.name,this,r),o&&this.element.addEventListener(this.name,this,e),this._$AH=e}handleEvent(e){var t;typeof this._$AH=="function"?this._$AH.call(((t=this.options)==null?void 0:t.host)??this.element,e):this._$AH.handleEvent(e)}}class lt{constructor(e,t,r){this.element=e,this.type=6,this._$AN=void 0,this._$AM=t,this.options=r}get _$AU(){return this._$AM._$AU}_$AI(e){H(this,e)}}const ct={I:Z},de=K.litHtmlPolyfillSupport;de==null||de(Y,Z),(K.litHtmlVersions??(K.litHtmlVersions=[])).push("3.3.0");const dt=(s,e,t)=>{const r=(t==null?void 0:t.renderBefore)??e;let i=r._$litPart$;if(i===void 0){const o=(t==null?void 0:t.renderBefore)??null;r._$litPart$=i=new Z(e.insertBefore(G(),o),o,void 0,t??{})}return i._$AI(s),i};/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const T=globalThis;let O=class extends B{constructor(){super(...arguments),this.renderOptions={host:this},this._$Do=void 0}createRenderRoot(){var t;const e=super.createRenderRoot();return(t=this.renderOptions).renderBefore??(t.renderBefore=e.firstChild),e}update(e){const t=this.render();this.hasUpdated||(this.renderOptions.isConnected=this.isConnected),super.update(e),this._$Do=dt(t,this.renderRoot,this.renderOptions)}connectedCallback(){var e;super.connectedCallback(),(e=this._$Do)==null||e.setConnected(!0)}disconnectedCallback(){var e;super.disconnectedCallback(),(e=this._$Do)==null||e.setConnected(!1)}render(){return M}};var Ie;O._$litElement$=!0,O.finalized=!0,(Ie=T.litElementHydrateSupport)==null||Ie.call(T,{LitElement:O});const he=T.litElementPolyfillSupport;he==null||he({LitElement:O});(T.litElementVersions??(T.litElementVersions=[])).push("4.2.0");/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const V=s=>(e,t)=>{t!==void 0?t.addInitializer(()=>{customElements.define(s,e)}):customElements.define(s,e)};/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const ht={attribute:!0,type:String,converter:ie,reflect:!1,hasChanged:me},pt=(s=ht,e,t)=>{const{kind:r,metadata:i}=t;let o=globalThis.litPropertyMetadata.get(i);if(o===void 0&&globalThis.litPropertyMetadata.set(i,o=new Map),r==="setter"&&((s=Object.create(s)).wrapped=!0),o.set(t.name,s),r==="accessor"){const{name:n}=t;return{set(d){const a=e.get.call(this);e.set.call(this,d),this.requestUpdate(n,a,s)},init(d){return d!==void 0&&this.C(n,void 0,s,d),d}}}if(r==="setter"){const{name:n}=t;return function(d){const a=this[n];e.call(this,d),this.requestUpdate(n,a,s)}}throw Error("Unsupported decorator location: "+r)};function x(s){return(e,t)=>typeof t=="object"?pt(s,e,t):((r,i,o)=>{const n=i.hasOwnProperty(o);return i.constructor.createProperty(o,r),n?Object.getOwnPropertyDescriptor(i,o):void 0})(s,e,t)}/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */function ut(s){return x({...s,state:!0,attribute:!1})}/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const ft=(s,e,t)=>(t.configurable=!0,t.enumerable=!0,Reflect.decorate&&typeof e!="object"&&Object.defineProperty(s,e,t),t);/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */function vt(s){return(e,t)=>{const{slot:r,selector:i}={},o="slot"+(r?`[name=${r}]`:":not([name])");return ft(e,t,{get(){var a;const n=(a=this.renderRoot)==null?void 0:a.querySelector(o),d=(n==null?void 0:n.assignedElements(s))??[];return i===void 0?d:d.filter(c=>c.matches(i))}})}}var mt=Object.defineProperty,gt=Object.getOwnPropertyDescriptor,Be=(s,e,t,r)=>{for(var i=r>1?void 0:r?gt(e,t):e,o=s.length-1,n;o>=0;o--)(n=s[o])&&(i=(r?n(e,t,i):n(i))||i);return r&&i&&mt(e,t,i),i};let oe=class extends O{updated(){if(this.items.forEach(s=>{s.classList.remove("first","last"),s.style.removeProperty("--radius"),s.style.removeProperty("--radius-hover")}),this.items.length>0){const s=this.items[0];s.classList.add("first"),s.style.setProperty("--radius","var(--radius-2) var(--radius-2) 0 0"),s.style.setProperty("--radius-hover","var(--radius-2) var(--radius-2) var(--radius-2) 0")}if(this.items.length>1){const s=this.items[this.items.length-1];s.classList.add("last"),s.style.setProperty("--radius","0 0 var(--radius-2) var(--radius-2)"),s.style.setProperty("--radius-hover","0 var(--radius-2) var(--radius-2) var(--radius-2)")}}render(){return A`<slot></slot>`}};oe.styles=q`
    :host {
      height: fit-content;
      display: flex;
      flex-direction: column;
      align-items: center;
    }
  `;Be([vt()],oe.prototype,"items",2);oe=Be([V("category-elem")],oe);/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const He={ATTRIBUTE:1,CHILD:2},De=s=>(...e)=>({_$litDirective$:s,values:e});let qe=class{constructor(e){}get _$AU(){return this._$AM._$AU}_$AT(e,t,r){this._$Ct=e,this._$AM=t,this._$Ci=r}_$AS(e,t){return this.update(e,t)}update(e,t){return this.render(...t)}};/**
 * @license
 * Copyright 2018 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const Ze="important",$t=" !"+Ze,yt=De(class extends qe{constructor(s){var e;if(super(s),s.type!==He.ATTRIBUTE||s.name!=="style"||((e=s.strings)==null?void 0:e.length)>2)throw Error("The `styleMap` directive must be used in the `style` attribute and must be the only part in the attribute.")}render(s){return Object.keys(s).reduce((e,t)=>{const r=s[t];return r==null?e:e+`${t=t.includes("-")?t:t.replace(/(?:^(webkit|moz|ms|o)|)(?=[A-Z])/g,"-$&").toLowerCase()}:${r};`},"")}update(s,[e]){const{style:t}=s.element;if(this.ft===void 0)return this.ft=new Set(Object.keys(e)),this.render(e);for(const r of this.ft)e[r]==null&&(this.ft.delete(r),r.includes("-")?t.removeProperty(r):t[r]=null);for(const r in e){const i=e[r];if(i!=null){this.ft.add(r);const o=typeof i=="string"&&i.endsWith($t);r.includes("-")||o?t.setProperty(r,o?i.slice(0,-11):i,o?Ze:""):t[r]=i}}return M}});var bt=Object.defineProperty,_t=Object.getOwnPropertyDescriptor,te=(s,e,t,r)=>{for(var i=r>1?void 0:r?_t(e,t):e,o=s.length-1,n;o>=0;o--)(n=s[o])&&(i=(r?n(e,t,i):n(i))||i);return r&&i&&bt(e,t,i),i};let L=class extends O{constructor(){super(...arguments),this.href="#",this.value="",this.bgColor="white",this.textColor="black"}render(){const s={first:this.classList.contains("first"),last:this.classList.contains("last")},e=Object.entries(s).filter(([,t])=>t).map(([t])=>t).join(" ");return A`
      <a
        part="item"
        href="${this.href}"
        class="${e}"
        style=${yt({"--hover-bg-color":this.bgColor,"--hover-color":this.textColor})}
      >
        <span class="icon"><slot></slot></span>
        <span class="label">${this.value}</span>
      </a>
    `}};L.styles=q`
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
  `;te([x({type:String})],L.prototype,"href",2);te([x({type:String})],L.prototype,"value",2);te([x({type:String})],L.prototype,"bgColor",2);te([x({type:String})],L.prototype,"textColor",2);L=te([V("category-item")],L);var wt=Object.defineProperty,xt=Object.getOwnPropertyDescriptor,C=(s,e,t,r)=>{for(var i=r>1?void 0:r?xt(e,t):e,o=s.length-1,n;o>=0;o--)(n=s[o])&&(i=(r?n(e,t,i):n(i))||i);return r&&i&&wt(e,t,i),i};let k=class extends O{constructor(){super(...arguments),this.pId=0,this.cId=0,this.ccId=0,this.pName="",this.src="",this.desc="",this.pReg="",this.defaultVariant=0,this.variants=null,this.checkedIds=new Set}get getImageUrl(){return`/pdr/img/${this.src}`}get getDate(){const s=new Date(this.pReg),e=s.getFullYear(),t=String(s.getMonth()+1).padStart(2,"0");return`${e}.${t}`}get getDefaultURI(){return this.variants&&this.defaultVariant!==0?`/pd/detail/${this.pId}_${this.defaultVariant}`:`/pd/detail/${this.pId}`}render(){var s;return A`
            <div class="p-item">
                <a href="${this.getDefaultURI}" class="p-img-container" target="_blank">
                    <img src=${this.getImageUrl}
                         onerror="this.onerror=null; this.src='https://placehold.co/200';"
                         alt="${this.pId}" class="p-img" loading="lazy">
                </a>
                <div class="p-content">
                    <a class="p-name" href="${this.getDefaultURI}" target="_blank">${this.pName}</a>
                    <div class="p-desc">${this.desc}</div>
                    <div class="p-reg">등록일 ${this.getDate}</div>
                </div>
                <div class="p-prices">
                    ${(s=this.variants)==null?void 0:s.map(e=>this.priceRender(e))}
                </div>
            </div>
        `}priceRender(s){const e=this.checkedIds.has(s.id),t=A`
            <svg xmlns="http://www.w3.org/2000/svg" enable-background="new 0 0 24 24" viewBox="0 -960 960 960" fill="currentColor">
                <path d="M440-280h80v-160h160v-80H520v-160h-80v160H280v80h160v160ZM200-120q-33 0-56.5-23.5T120-200v-560q0-33 23.5-56.5T200-840h560q33 0 56.5 23.5T840-760v560q0 33-23.5 56.5T760-120H200Z"/>
            </svg>
        `,r=A`
            <svg xmlns="http://www.w3.org/2000/svg" enable-background="new 0 0 24 24" viewBox="0 0 24 24" fill="currentColor">
                <rect fill="none" height="24" width="24"/>
                <path d="M3,5v14c0,1.1,0.9,2,2,2h14c1.1,0,2-0.9,2-2V5c0-1.1-0.9-2-2-2H5C3.9,3,3,3.9,3,5z M16.3,16.29L16.3,16.29 c-0.39,0.39-1.02,0.39-1.41,0L12,13.41l-2.89,2.89c-0.39,0.39-1.02,0.39-1.41,0l0,0c-0.39-0.39-0.39-1.02,0-1.41L10.59,12L7.7,9.11 c-0.39-0.39-0.39-1.02,0-1.41l0,0c0.39-0.39,1.02-0.39,1.41,0L12,10.59l2.89-2.88c0.39-0.39,1.02-0.39,1.41,0l0,0 c0.39,0.39,0.39,1.02,0,1.41L13.41,12l2.89,2.88C16.68,15.27,16.68,15.91,16.3,16.29z"/>
            </svg>
        `;return s.schedule?A`
            <div class="price-item">
                <a class="price-name" href="/pd/detail/${this.pId}_${s.id}" target="_blank">${s.name}</a>
                <a class="price-value" href="/pd/detail/${this.pId}_${s.id}" target="_blank">가격비교예정</a>
                <span class="material-icons price-plus disabled">${t}</span>
            </div>
        `:A`
            <div class="price-item">
                <a class="price-name" href="/pd/detail/${this.pId}_${s.id}" target="_blank">${s.name}</a>
                <a class="price-value" href="/pd/detail/${this.pId}_${s.id}" target="_blank">${s.price.toLocaleString()}원</a>
                <span class="material-icons price-plus ${e&&"checked"}" @click=${()=>this.togglePriceCheck(s)}>
                    ${e?r:t}
                </span>
            </div>
        `}dispatchSelect(s){const e={...s,name:this.pName+" "+s.name};this.dispatchEvent(new CustomEvent("select",{detail:{product:e},bubbles:!0,composed:!0}))}togglePriceCheck(s){const e=s.id;this.checkedIds.has(e)?this.checkedIds.delete(e):this.checkedIds.add(e),this.requestUpdate();const t={...s,name:`${this.pName} ${s.name}`};this.dispatchEvent(new CustomEvent("select",{detail:{categoryId:this.cId,categoryChildId:this.ccId,product:t,checked:this.checkedIds.has(e)},bubbles:!0,composed:!0}))}setChecked(s,e){e?this.checkedIds.add(s):this.checkedIds.delete(s),this.requestUpdate()}};k.styles=q`
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
    `;C([x({type:Number})],k.prototype,"pId",2);C([x({type:Number})],k.prototype,"cId",2);C([x({type:Number})],k.prototype,"ccId",2);C([x({type:String})],k.prototype,"pName",2);C([x({type:String})],k.prototype,"src",2);C([x({type:String})],k.prototype,"desc",2);C([x({type:String})],k.prototype,"pReg",2);C([x({type:Number})],k.prototype,"defaultVariant",2);C([x({type:Array})],k.prototype,"variants",2);C([ut()],k.prototype,"checkedIds",2);k=C([V("product-item")],k);/**
 * @license
 * Copyright 2020 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const{I:At}=ct,Oe=()=>document.createComment(""),J=(s,e,t)=>{var o;const r=s._$AA.parentNode,i=e===void 0?s._$AB:e._$AA;if(t===void 0){const n=r.insertBefore(Oe(),i),d=r.insertBefore(Oe(),i);t=new At(n,d,s,s.options)}else{const n=t._$AB.nextSibling,d=t._$AM,a=d!==s;if(a){let c;(o=t._$AQ)==null||o.call(t,s),t._$AM=s,t._$AP!==void 0&&(c=s._$AU)!==d._$AU&&t._$AP(c)}if(n!==i||a){let c=t._$AA;for(;c!==n;){const u=c.nextSibling;r.insertBefore(c,i),c=u}}}return t},j=(s,e,t=s)=>(s._$AI(e,t),s),St={},Et=(s,e=St)=>s._$AH=e,kt=s=>s._$AH,pe=s=>{var r;(r=s._$AP)==null||r.call(s,!1,!0);let e=s._$AA;const t=s._$AB.nextSibling;for(;e!==t;){const i=e.nextSibling;e.remove(),e=i}};/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const ze=(s,e,t)=>{const r=new Map;for(let i=e;i<=t;i++)r.set(s[i],i);return r},Pt=De(class extends qe{constructor(s){if(super(s),s.type!==He.CHILD)throw Error("repeat() can only be used in text expressions")}dt(s,e,t){let r;t===void 0?t=e:e!==void 0&&(r=e);const i=[],o=[];let n=0;for(const d of s)i[n]=r?r(d,n):n,o[n]=t(d,n),n++;return{values:o,keys:i}}render(s,e,t){return this.dt(s,e,t).values}update(s,[e,t,r]){const i=kt(s),{values:o,keys:n}=this.dt(e,t,r);if(!Array.isArray(i))return this.ut=n,o;const d=this.ut??(this.ut=[]),a=[];let c,u,l=0,h=i.length-1,v=0,g=o.length-1;for(;l<=h&&v<=g;)if(i[l]===null)l++;else if(i[h]===null)h--;else if(d[l]===n[v])a[v]=j(i[l],o[v]),l++,v++;else if(d[h]===n[g])a[g]=j(i[h],o[g]),h--,g--;else if(d[l]===n[g])a[g]=j(i[l],o[g]),J(s,a[g+1],i[l]),l++,g--;else if(d[h]===n[v])a[v]=j(i[h],o[v]),J(s,i[l],i[h]),h--,v++;else if(c===void 0&&(c=ze(n,v,g),u=ze(d,l,h)),c.has(d[l]))if(c.has(d[h])){const _=u.get(n[v]),P=_!==void 0?i[_]:null;if(P===null){const b=J(s,i[l]);j(b,o[v]),a[v]=b}else a[v]=j(P,o[v]),J(s,i[l],P),i[_]=null;v++}else pe(i[h]),h--;else pe(i[l]),l++;for(;v<=g;){const _=J(s,a[g+1]);j(_,o[v]),a[v++]=_}for(;l<=h;){const _=i[l++];_!==null&&pe(_)}return this.ut=n,Et(s,a),M}});var Ct=Object.defineProperty,Ot=Object.getOwnPropertyDescriptor,ae=(s,e,t,r)=>{for(var i=r>1?void 0:r?Ot(e,t):e,o=s.length-1,n;o>=0;o--)(n=s[o])&&(i=(r?n(e,t,i):n(i))||i);return r&&i&&Ct(e,t,i),i};let D=class extends O{constructor(){super(...arguments),this.categoryMap=new Map,this.products=[]}firstUpdated(s){this.addEventListener("select",this.handleSelect)}updated(s){s.has("basketSideEl")&&this.basketSideEl&&this.basketSideEl.addEventListener("remove",e=>this.onBasketRemove(e))}setProducts(s){this.products=s}updateProductById(s,e){this.products=this.products.map(t=>t.id===s?{...t,...e}:t)}setChecked(s,e,t){const r=this.renderRoot.querySelectorAll("product-item");for(const i of r)if(i.pId===s){i.setChecked(e,t);break}}handleSelect(s){var l;if(!this.basketSideEl||!this.categoryMap)return;const{categoryId:e,categoryChildId:t,product:r,checked:i}=s.detail,o=this.categoryMap.get(e);if(!o)return;const n=((l=o.variants.get(t))==null?void 0:l.name)||o.name;if(!n)return;const d=this.basketSideEl,{id:a,name:c="",price:u}=r;i?d.addProduct(n,a,c??"",u):d.removeProduct(n,a)}onBasketRemove(s){if(!this.basketSideEl)return;s.detail.product.forEach(t=>{const r=Array.from(this.renderRoot.querySelectorAll("product-item")).find(i=>{var n;return(n=i.variants)==null?void 0:n.some(d=>d.id===t.id)});r?r.setChecked(t.id,!1):console.warn("상품을 찾을 수 없습니다 (priceId):",t.id)})}getVariantToPrice(s){return s.variants.length>0?s.variants.map(e=>({id:e.id,name:e.name,price:e.price,schedule:e.price==0})):[{id:s.id,name:null,price:s.price,schedule:s.price==0}]}render(){return A`
            <div id="product-list">
                ${Pt(this.products,s=>s.id,s=>A`
                  <product-item
                          .pId=${s.id}
                          .cId=${s.categoryId}
                          .ccId=${s.categoryChildId}
                          .pName=${s.name}
                          .src=${s.simpleImg}
                          .desc=${s.desc}
                          .pReg=${s.reg}
                          .defaultVariant=${s.defaultVariant}
                          .variants=${this.getVariantToPrice(s)}
                  ></product-item>
                `)}
            </div>
        `}};D.styles=q`
        #product-list {
            display: flex;
            flex-direction: column;
            gap: var(--size-1);
        }
    `;ae([x({type:Object})],D.prototype,"categoryMap",2);ae([x({type:Object})],D.prototype,"basketSideEl",2);ae([x({type:Array})],D.prototype,"products",2);D=ae([V("product-list")],D);function zt(s){return s&&s.__esModule&&Object.prototype.hasOwnProperty.call(s,"default")?s.default:s}var ue={exports:{}},Ue;function Ut(){return Ue||(Ue=1,function(s){var e=function(){var t=String.fromCharCode,r="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",i="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-$",o={};function n(a,c){if(!o[a]){o[a]={};for(var u=0;u<a.length;u++)o[a][a.charAt(u)]=u}return o[a][c]}var d={compressToBase64:function(a){if(a==null)return"";var c=d._compress(a,6,function(u){return r.charAt(u)});switch(c.length%4){default:case 0:return c;case 1:return c+"===";case 2:return c+"==";case 3:return c+"="}},decompressFromBase64:function(a){return a==null?"":a==""?null:d._decompress(a.length,32,function(c){return n(r,a.charAt(c))})},compressToUTF16:function(a){return a==null?"":d._compress(a,15,function(c){return t(c+32)})+" "},decompressFromUTF16:function(a){return a==null?"":a==""?null:d._decompress(a.length,16384,function(c){return a.charCodeAt(c)-32})},compressToUint8Array:function(a){for(var c=d.compress(a),u=new Uint8Array(c.length*2),l=0,h=c.length;l<h;l++){var v=c.charCodeAt(l);u[l*2]=v>>>8,u[l*2+1]=v%256}return u},decompressFromUint8Array:function(a){if(a==null)return d.decompress(a);for(var c=new Array(a.length/2),u=0,l=c.length;u<l;u++)c[u]=a[u*2]*256+a[u*2+1];var h=[];return c.forEach(function(v){h.push(t(v))}),d.decompress(h.join(""))},compressToEncodedURIComponent:function(a){return a==null?"":d._compress(a,6,function(c){return i.charAt(c)})},decompressFromEncodedURIComponent:function(a){return a==null?"":a==""?null:(a=a.replace(/ /g,"+"),d._decompress(a.length,32,function(c){return n(i,a.charAt(c))}))},compress:function(a){return d._compress(a,16,function(c){return t(c)})},_compress:function(a,c,u){if(a==null)return"";var l,h,v={},g={},_="",P="",b="",S=2,w=3,$=2,y=[],p=0,m=0,f;for(f=0;f<a.length;f+=1)if(_=a.charAt(f),Object.prototype.hasOwnProperty.call(v,_)||(v[_]=w++,g[_]=!0),P=b+_,Object.prototype.hasOwnProperty.call(v,P))b=P;else{if(Object.prototype.hasOwnProperty.call(g,b)){if(b.charCodeAt(0)<256){for(l=0;l<$;l++)p=p<<1,m==c-1?(m=0,y.push(u(p)),p=0):m++;for(h=b.charCodeAt(0),l=0;l<8;l++)p=p<<1|h&1,m==c-1?(m=0,y.push(u(p)),p=0):m++,h=h>>1}else{for(h=1,l=0;l<$;l++)p=p<<1|h,m==c-1?(m=0,y.push(u(p)),p=0):m++,h=0;for(h=b.charCodeAt(0),l=0;l<16;l++)p=p<<1|h&1,m==c-1?(m=0,y.push(u(p)),p=0):m++,h=h>>1}S--,S==0&&(S=Math.pow(2,$),$++),delete g[b]}else for(h=v[b],l=0;l<$;l++)p=p<<1|h&1,m==c-1?(m=0,y.push(u(p)),p=0):m++,h=h>>1;S--,S==0&&(S=Math.pow(2,$),$++),v[P]=w++,b=String(_)}if(b!==""){if(Object.prototype.hasOwnProperty.call(g,b)){if(b.charCodeAt(0)<256){for(l=0;l<$;l++)p=p<<1,m==c-1?(m=0,y.push(u(p)),p=0):m++;for(h=b.charCodeAt(0),l=0;l<8;l++)p=p<<1|h&1,m==c-1?(m=0,y.push(u(p)),p=0):m++,h=h>>1}else{for(h=1,l=0;l<$;l++)p=p<<1|h,m==c-1?(m=0,y.push(u(p)),p=0):m++,h=0;for(h=b.charCodeAt(0),l=0;l<16;l++)p=p<<1|h&1,m==c-1?(m=0,y.push(u(p)),p=0):m++,h=h>>1}S--,S==0&&(S=Math.pow(2,$),$++),delete g[b]}else for(h=v[b],l=0;l<$;l++)p=p<<1|h&1,m==c-1?(m=0,y.push(u(p)),p=0):m++,h=h>>1;S--,S==0&&(S=Math.pow(2,$),$++)}for(h=2,l=0;l<$;l++)p=p<<1|h&1,m==c-1?(m=0,y.push(u(p)),p=0):m++,h=h>>1;for(;;)if(p=p<<1,m==c-1){y.push(u(p));break}else m++;return y.join("")},decompress:function(a){return a==null?"":a==""?null:d._decompress(a.length,32768,function(c){return a.charCodeAt(c)})},_decompress:function(a,c,u){var l=[],h=4,v=4,g=3,_="",P=[],b,S,w,$,y,p,m,f={val:u(0),position:c,index:1};for(b=0;b<3;b+=1)l[b]=b;for(w=0,y=Math.pow(2,2),p=1;p!=y;)$=f.val&f.position,f.position>>=1,f.position==0&&(f.position=c,f.val=u(f.index++)),w|=($>0?1:0)*p,p<<=1;switch(w){case 0:for(w=0,y=Math.pow(2,8),p=1;p!=y;)$=f.val&f.position,f.position>>=1,f.position==0&&(f.position=c,f.val=u(f.index++)),w|=($>0?1:0)*p,p<<=1;m=t(w);break;case 1:for(w=0,y=Math.pow(2,16),p=1;p!=y;)$=f.val&f.position,f.position>>=1,f.position==0&&(f.position=c,f.val=u(f.index++)),w|=($>0?1:0)*p,p<<=1;m=t(w);break;case 2:return""}for(l[3]=m,S=m,P.push(m);;){if(f.index>a)return"";for(w=0,y=Math.pow(2,g),p=1;p!=y;)$=f.val&f.position,f.position>>=1,f.position==0&&(f.position=c,f.val=u(f.index++)),w|=($>0?1:0)*p,p<<=1;switch(m=w){case 0:for(w=0,y=Math.pow(2,8),p=1;p!=y;)$=f.val&f.position,f.position>>=1,f.position==0&&(f.position=c,f.val=u(f.index++)),w|=($>0?1:0)*p,p<<=1;l[v++]=t(w),m=v-1,h--;break;case 1:for(w=0,y=Math.pow(2,16),p=1;p!=y;)$=f.val&f.position,f.position>>=1,f.position==0&&(f.position=c,f.val=u(f.index++)),w|=($>0?1:0)*p,p<<=1;l[v++]=t(w),m=v-1,h--;break;case 2:return P.join("")}if(h==0&&(h=Math.pow(2,g),g++),l[m])_=l[m];else if(m===v)_=S+S.charAt(0);else return null;P.push(_),l[v++]=S+_.charAt(0),h--,S=_,h==0&&(h=Math.pow(2,g),g++)}}};return d}();s!=null?s.exports=e:typeof angular<"u"&&angular!=null&&angular.module("LZString",[]).factory("LZString",function(){return e})}(ue)),ue.exports}var Mt=Ut();const Me=zt(Mt);var It=Object.defineProperty,jt=Object.getOwnPropertyDescriptor,$e=(s,e,t,r)=>{for(var i=r>1?void 0:r?jt(e,t):e,o=s.length-1,n;o>=0;o--)(n=s[o])&&(i=(r?n(e,t,i):n(i))||i);return r&&i&&It(e,t,i),i};let X=class extends O{constructor(){super(...arguments),this.category=[],this.basket=new Map}addProduct(s,e,t,r){let i=[];if(this.basket.has(s)&&(i=this.basket.get(s)),!i.some(n=>n.id===e))i.push({id:e,name:t,price:r,amount:1});else{let n=this.getProductAmount(i,e);n!=null&&(n++,i=this.updateAmountImmutable(i,e,n))}this.basket.set(s,i),this.requestUpdate()}minusProduct(s,e){let t=[];if(this.basket.has(s)&&(t=this.basket.get(s)),t.some(i=>i.id===e)){let i=this.getProductAmount(t,e);if(i)if(i>1)i--,this.updateAmountImmutable(t,e,i),this.dispatchChange();else{const o=this.findById(t,e);t=this.removeById(t,e),o&&this.dispatchRemove(o)}}this.basket.set(s,t),this.requestUpdate()}removeProduct(s,e){let t=[];if(this.basket.has(s)&&(t=this.basket.get(s)),t.some(i=>i.id===e)){const i=this.findById(t,e);t=this.removeById(t,e),i&&this.dispatchRemove(i)}if(t.length==0){this.basket.delete(s),this.requestUpdate();return}this.basket.set(s,t),this.requestUpdate()}productClear(){this.dispatchRemoves(),this.basket.clear(),this.requestUpdate()}getProductAmount(s,e){const t=s.find(r=>r.id===e);return t!=null?t.amount:null}updateAmountImmutable(s,e,t){return s.map(r=>r.id===e?{...r,amount:t}:r)}findById(s,e){return s.find(t=>t.id===e)}removeById(s,e){return s.filter(t=>t.id!==e)}get calculateTotalPrice(){let s=0;for(const[,e]of this.basket)for(const t of e)s+=t.price*t.amount;return s}get compressedData(){const s=Array.from(this.basket.entries()),e=JSON.stringify(s);return Me.compressToBase64(e)}set decompressedData(s){const e=Me.decompressFromBase64(s);if(!e)throw new Error("압축 해제 실패");const t=JSON.parse(e);this.basket=new Map(Object.entries(t))}dispatchChange(){const s=this.basket;this.dispatchEvent(new CustomEvent("change",{detail:{basket:s},bubbles:!0,composed:!0}))}dispatchRemove(s){const e={id:s.id,price:s.price,name:s.name,schedule:!0};this.dispatchEvent(new CustomEvent("remove",{detail:{product:[e]},bubbles:!0,composed:!0}))}dispatchRemoves(){const s=Array.from(this.basket.values()).flat().map(e=>({id:e.id,name:e.name,price:e.price,schedule:!0}));this.dispatchEvent(new CustomEvent("remove",{detail:{product:s}}))}handleInput(s,e,t){const r=s.target,i=Number(r.value);if(i<1||isNaN(i))return;const o=this.basket.get(e);if(!o)return;const n=o.map(d=>d.id===t?{...d,amount:i}:d);this.basket.set(e,n),this.requestUpdate()}basketContentRenderer(){return this.basket.size===0?A`
      <div id="basket-empty">
        비어있습니다
      </div>
    `:this.category.length>0&&this.category.some(e=>this.basket.has(e.code))?A`
      ${this.category.filter(e=>this.basket.has(e.code)).sort((e,t)=>e.id-t.id).map(e=>{const t=this.basket.get(e.code);return A`
            <div class="basket-category">${e.name}</div>
            ${t.map(r=>A`
              <div class="basket-item">
                <div class="b-img">
                  <img src="https://placehold.co/600x400" alt="${r.id}" />
                </div>
                <div class="b-content">
                  <div class="b-pName" title=${r.name}>${r.name}</div>
                  <div class="bc-bottom">
                    <div class="b-pPrice">${r.price.toLocaleString()}원</div>
                    <div class="b-pAmount">수량 <input type="number" value=${r.amount} min="1" max="1000"
                                                     @input=${i=>this.handleInput(i,e.name,r.id)}></div>
                  </div>
                </div>
                <div class="b-remove" @click=${()=>this.removeProduct(e.code,r.id)}>
                  <span class="material-icons">close</span>
                </div>
              </div>
            `)}
          `})}
    `:A`
    ${Array.from(this.basket.entries()).map(([e,t])=>A`
        <div class="basket-category">${e.toUpperCase()}</div>
        ${t.map(r=>A`
          <div class="basket-item">
            <div class="b-img">
              <img src="https://placehold.co/600x400" alt="${r.id}" />
            </div>
            <div class="b-content">
              <div class="b-pName" title=${r.name}>${r.name}</div>
              <div class="bc-bottom">
                <div class="b-pPrice">${r.price.toLocaleString()}원</div>
                <div class="b-pAmount">수량 <input type="number" value=${r.amount} min="1" max="1000"
                                                 @input=${i=>this.handleInput(i,e,r.id)}></div>
              </div>
            </div>
            <div class="b-remove" @click=${()=>this.removeProduct(e,r.id)}><span class="material-icons">close</span></div>
          </div>
        `)}
      `)}
  `}render(){return A`
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
                <div id="check" class="btn">호환성</div>
                <div id="reset" class="btn" @click=${()=>this.productClear()}>초기화</div>
                <div id="buy" class="btn">구매</div>
            </div>
        </div>
    `}};X.styles=q`
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
  `;$e([x({type:Array})],X.prototype,"category",2);$e([x({type:Map})],X.prototype,"basket",2);X=$e([V("basket-side")],X);var Rt=Object.defineProperty,Tt=Object.getOwnPropertyDescriptor,ye=(s,e,t,r)=>{for(var i=r>1?void 0:r?Tt(e,t):e,o=s.length-1,n;o>=0;o--)(n=s[o])&&(i=(r?n(e,t,i):n(i))||i);return r&&i&&Rt(e,t,i),i};let ee=class extends O{constructor(){super(...arguments),this.filterRows=[],this.expanded=!1,this.dispatchFilterChange=()=>{const s={};this.renderRoot.querySelectorAll('input[type="checkbox"]:checked').forEach(t=>{const r=t.name,i=t.value;s[r]||(s[r]=[]),s[r].push(this.deserializeValue(i))}),this.dispatchEvent(new CustomEvent("change",{detail:s,bubbles:!0,composed:!0}))}}deserializeValue(s){try{return JSON.parse(s)}catch{return s}}render(){const s=this.filterRows.filter(e=>e.isFilterable).sort((e,t)=>e.displayOrder-t.displayOrder);return A`
      <div class="filter-container">
        ${s.map(e=>A`
          <div class="filter-row">
            <div class="filter-label" title=${e.tooltip??""}>
              ${e.displayName}
            </div>
            <div class="filter-options ${this.expanded?"":"collapsed"}">
              ${e.valueList.slice().sort((t,r)=>r.weight-t.weight).map(t=>{let r;if(typeof t.value=="object")r=JSON.stringify(t.value);else{const i=Number(t.value);if(e.unit==="GB"&&!isNaN(i))if(i>=1e3){const o=i/1e3;r=`${o%1===0?o:o.toFixed(1)}TB`}else r=`${i}GB`;else r=`${t.value}${e.unit??""}`}return A`
                    <label>
                      <input
                        type="checkbox"
                        name=${e.attributeKey}
                        .value=${JSON.stringify(t.value)}
                        @change=${this.dispatchFilterChange}>
                      ${r}
                    </label>
                  `})}
            </div>
          </div>
        `)}
        <div class="filter-bottom" @click=${()=>this.expanded=!this.expanded}>
          ${this.expanded?"접기":"펼치기"}
        </div>
      </div>
    `}};ee.styles=q`
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
  `;ye([x({type:Array})],ee.prototype,"filterRows",2);ye([x({type:Boolean})],ee.prototype,"expanded",2);ee=ye([V("filter-panel")],ee);
