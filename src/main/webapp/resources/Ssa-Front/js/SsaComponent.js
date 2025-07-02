(function(){const e=document.createElement("link").relList;if(e&&e.supports&&e.supports("modulepreload"))return;for(const i of document.querySelectorAll('link[rel="modulepreload"]'))r(i);new MutationObserver(i=>{for(const o of i)if(o.type==="childList")for(const n of o.addedNodes)n.tagName==="LINK"&&n.rel==="modulepreload"&&r(n)}).observe(document,{childList:!0,subtree:!0});function t(i){const o={};return i.integrity&&(o.integrity=i.integrity),i.referrerPolicy&&(o.referrerPolicy=i.referrerPolicy),i.crossOrigin==="use-credentials"?o.credentials="include":i.crossOrigin==="anonymous"?o.credentials="omit":o.credentials="same-origin",o}function r(i){if(i.ep)return;i.ep=!0;const o=t(i);fetch(i.href,o)}})();/**
 * @license
 * Copyright 2019 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const te=globalThis,ue=te.ShadowRoot&&(te.ShadyCSS===void 0||te.ShadyCSS.nativeShadow)&&"adoptedStyleSheets"in Document.prototype&&"replace"in CSSStyleSheet.prototype,fe=Symbol(),$e=new WeakMap;let Me=class{constructor(e,t,r){if(this._$cssResult$=!0,r!==fe)throw Error("CSSResult is not constructable. Use `unsafeCSS` or `css` instead.");this.cssText=e,this.t=t}get styleSheet(){let e=this.o;const t=this.t;if(ue&&e===void 0){const r=t!==void 0&&t.length===1;r&&(e=$e.get(t)),e===void 0&&((this.o=e=new CSSStyleSheet).replaceSync(this.cssText),r&&$e.set(t,e))}return e}toString(){return this.cssText}};const qe=s=>new Me(typeof s=="string"?s:s+"",void 0,fe),Y=(s,...e)=>{const t=s.length===1?s[0]:e.reduce((r,i,o)=>r+(n=>{if(n._$cssResult$===!0)return n.cssText;if(typeof n=="number")return n;throw Error("Value passed to 'css' function must be a 'css' function result: "+n+". Use 'unsafeCSS' to pass non-literal values, but take care to ensure page security.")})(i)+s[o+1],s[0]);return new Me(t,s,fe)},We=(s,e)=>{if(ue)s.adoptedStyleSheets=e.map(t=>t instanceof CSSStyleSheet?t:t.styleSheet);else for(const t of e){const r=document.createElement("style"),i=te.litNonce;i!==void 0&&r.setAttribute("nonce",i),r.textContent=t.cssText,s.appendChild(r)}},ye=ue?s=>s:s=>s instanceof CSSStyleSheet?(e=>{let t="";for(const r of e.cssRules)t+=r.cssText;return qe(t)})(s):s;/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const{is:Ze,defineProperty:Je,getOwnPropertyDescriptor:Ke,getOwnPropertyNames:Qe,getOwnPropertySymbols:Ge,getPrototypeOf:Ve}=Object,O=globalThis,be=O.trustedTypes,Xe=be?be.emptyScript:"",ae=O.reactiveElementPolyfillSupport,J=(s,e)=>s,se={toAttribute(s,e){switch(e){case Boolean:s=s?Xe:null;break;case Object:case Array:s=s==null?s:JSON.stringify(s)}return s},fromAttribute(s,e){let t=s;switch(e){case Boolean:t=s!==null;break;case Number:t=s===null?null:Number(s);break;case Object:case Array:try{t=JSON.parse(s)}catch{t=null}}return t}},ve=(s,e)=>!Ze(s,e),_e={attribute:!0,type:String,converter:se,reflect:!1,useDefault:!1,hasChanged:ve};Symbol.metadata??(Symbol.metadata=Symbol("metadata")),O.litPropertyMetadata??(O.litPropertyMetadata=new WeakMap);let B=class extends HTMLElement{static addInitializer(e){this._$Ei(),(this.l??(this.l=[])).push(e)}static get observedAttributes(){return this.finalize(),this._$Eh&&[...this._$Eh.keys()]}static createProperty(e,t=_e){if(t.state&&(t.attribute=!1),this._$Ei(),this.prototype.hasOwnProperty(e)&&((t=Object.create(t)).wrapped=!0),this.elementProperties.set(e,t),!t.noAccessor){const r=Symbol(),i=this.getPropertyDescriptor(e,r,t);i!==void 0&&Je(this.prototype,e,i)}}static getPropertyDescriptor(e,t,r){const{get:i,set:o}=Ke(this.prototype,e)??{get(){return this[t]},set(n){this[t]=n}};return{get:i,set(n){const h=i==null?void 0:i.call(this);o==null||o.call(this,n),this.requestUpdate(e,h,r)},configurable:!0,enumerable:!0}}static getPropertyOptions(e){return this.elementProperties.get(e)??_e}static _$Ei(){if(this.hasOwnProperty(J("elementProperties")))return;const e=Ve(this);e.finalize(),e.l!==void 0&&(this.l=[...e.l]),this.elementProperties=new Map(e.elementProperties)}static finalize(){if(this.hasOwnProperty(J("finalized")))return;if(this.finalized=!0,this._$Ei(),this.hasOwnProperty(J("properties"))){const t=this.properties,r=[...Qe(t),...Ge(t)];for(const i of r)this.createProperty(i,t[i])}const e=this[Symbol.metadata];if(e!==null){const t=litPropertyMetadata.get(e);if(t!==void 0)for(const[r,i]of t)this.elementProperties.set(r,i)}this._$Eh=new Map;for(const[t,r]of this.elementProperties){const i=this._$Eu(t,r);i!==void 0&&this._$Eh.set(i,t)}this.elementStyles=this.finalizeStyles(this.styles)}static finalizeStyles(e){const t=[];if(Array.isArray(e)){const r=new Set(e.flat(1/0).reverse());for(const i of r)t.unshift(ye(i))}else e!==void 0&&t.push(ye(e));return t}static _$Eu(e,t){const r=t.attribute;return r===!1?void 0:typeof r=="string"?r:typeof e=="string"?e.toLowerCase():void 0}constructor(){super(),this._$Ep=void 0,this.isUpdatePending=!1,this.hasUpdated=!1,this._$Em=null,this._$Ev()}_$Ev(){var e;this._$ES=new Promise(t=>this.enableUpdating=t),this._$AL=new Map,this._$E_(),this.requestUpdate(),(e=this.constructor.l)==null||e.forEach(t=>t(this))}addController(e){var t;(this._$EO??(this._$EO=new Set)).add(e),this.renderRoot!==void 0&&this.isConnected&&((t=e.hostConnected)==null||t.call(e))}removeController(e){var t;(t=this._$EO)==null||t.delete(e)}_$E_(){const e=new Map,t=this.constructor.elementProperties;for(const r of t.keys())this.hasOwnProperty(r)&&(e.set(r,this[r]),delete this[r]);e.size>0&&(this._$Ep=e)}createRenderRoot(){const e=this.shadowRoot??this.attachShadow(this.constructor.shadowRootOptions);return We(e,this.constructor.elementStyles),e}connectedCallback(){var e;this.renderRoot??(this.renderRoot=this.createRenderRoot()),this.enableUpdating(!0),(e=this._$EO)==null||e.forEach(t=>{var r;return(r=t.hostConnected)==null?void 0:r.call(t)})}enableUpdating(e){}disconnectedCallback(){var e;(e=this._$EO)==null||e.forEach(t=>{var r;return(r=t.hostDisconnected)==null?void 0:r.call(t)})}attributeChangedCallback(e,t,r){this._$AK(e,r)}_$ET(e,t){var o;const r=this.constructor.elementProperties.get(e),i=this.constructor._$Eu(e,r);if(i!==void 0&&r.reflect===!0){const n=(((o=r.converter)==null?void 0:o.toAttribute)!==void 0?r.converter:se).toAttribute(t,r.type);this._$Em=e,n==null?this.removeAttribute(i):this.setAttribute(i,n),this._$Em=null}}_$AK(e,t){var o,n;const r=this.constructor,i=r._$Eh.get(e);if(i!==void 0&&this._$Em!==i){const h=r.getPropertyOptions(i),a=typeof h.converter=="function"?{fromAttribute:h.converter}:((o=h.converter)==null?void 0:o.fromAttribute)!==void 0?h.converter:se;this._$Em=i,this[i]=a.fromAttribute(t,h.type)??((n=this._$Ej)==null?void 0:n.get(i))??null,this._$Em=null}}requestUpdate(e,t,r){var i;if(e!==void 0){const o=this.constructor,n=this[e];if(r??(r=o.getPropertyOptions(e)),!((r.hasChanged??ve)(n,t)||r.useDefault&&r.reflect&&n===((i=this._$Ej)==null?void 0:i.get(e))&&!this.hasAttribute(o._$Eu(e,r))))return;this.C(e,t,r)}this.isUpdatePending===!1&&(this._$ES=this._$EP())}C(e,t,{useDefault:r,reflect:i,wrapped:o},n){r&&!(this._$Ej??(this._$Ej=new Map)).has(e)&&(this._$Ej.set(e,n??t??this[e]),o!==!0||n!==void 0)||(this._$AL.has(e)||(this.hasUpdated||r||(t=void 0),this._$AL.set(e,t)),i===!0&&this._$Em!==e&&(this._$Eq??(this._$Eq=new Set)).add(e))}async _$EP(){this.isUpdatePending=!0;try{await this._$ES}catch(t){Promise.reject(t)}const e=this.scheduleUpdate();return e!=null&&await e,!this.isUpdatePending}scheduleUpdate(){return this.performUpdate()}performUpdate(){var r;if(!this.isUpdatePending)return;if(!this.hasUpdated){if(this.renderRoot??(this.renderRoot=this.createRenderRoot()),this._$Ep){for(const[o,n]of this._$Ep)this[o]=n;this._$Ep=void 0}const i=this.constructor.elementProperties;if(i.size>0)for(const[o,n]of i){const{wrapped:h}=n,a=this[o];h!==!0||this._$AL.has(o)||a===void 0||this.C(o,void 0,n,a)}}let e=!1;const t=this._$AL;try{e=this.shouldUpdate(t),e?(this.willUpdate(t),(r=this._$EO)==null||r.forEach(i=>{var o;return(o=i.hostUpdate)==null?void 0:o.call(i)}),this.update(t)):this._$EM()}catch(i){throw e=!1,this._$EM(),i}e&&this._$AE(t)}willUpdate(e){}_$AE(e){var t;(t=this._$EO)==null||t.forEach(r=>{var i;return(i=r.hostUpdated)==null?void 0:i.call(r)}),this.hasUpdated||(this.hasUpdated=!0,this.firstUpdated(e)),this.updated(e)}_$EM(){this._$AL=new Map,this.isUpdatePending=!1}get updateComplete(){return this.getUpdateComplete()}getUpdateComplete(){return this._$ES}shouldUpdate(e){return!0}update(e){this._$Eq&&(this._$Eq=this._$Eq.forEach(t=>this._$ET(t,this[t]))),this._$EM()}updated(e){}firstUpdated(e){}};B.elementStyles=[],B.shadowRootOptions={mode:"open"},B[J("elementProperties")]=new Map,B[J("finalized")]=new Map,ae==null||ae({ReactiveElement:B}),(O.reactiveElementVersions??(O.reactiveElementVersions=[])).push("2.1.0");/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const K=globalThis,ie=K.trustedTypes,we=ie?ie.createPolicy("lit-html",{createHTML:s=>s}):void 0,je="$lit$",z=`lit$${Math.random().toFixed(9).slice(2)}$`,Re="?"+z,Ye=`<${Re}>`,N=document,Q=()=>N.createComment(""),G=s=>s===null||typeof s!="object"&&typeof s!="function",me=Array.isArray,Fe=s=>me(s)||typeof(s==null?void 0:s[Symbol.iterator])=="function",ce=`[ 	
\f\r]`,W=/<(?:(!--|\/[^a-zA-Z])|(\/?[a-zA-Z][^>\s]*)|(\/?$))/g,Ae=/-->/g,xe=/>/g,j=RegExp(`>|${ce}(?:([^\\s"'>=/]+)(${ce}*=${ce}*(?:[^ 	
\f\r"'\`<>=]|("|')|))|$)`,"g"),Ee=/'/g,Se=/"/g,Te=/^(?:script|style|textarea|title)$/i,et=s=>(e,...t)=>({_$litType$:s,strings:e,values:t}),E=et(1),U=Symbol.for("lit-noChange"),x=Symbol.for("lit-nothing"),ke=new WeakMap,T=N.createTreeWalker(N,129);function Le(s,e){if(!me(s)||!s.hasOwnProperty("raw"))throw Error("invalid template strings array");return we!==void 0?we.createHTML(e):e}const tt=(s,e)=>{const t=s.length-1,r=[];let i,o=e===2?"<svg>":e===3?"<math>":"",n=W;for(let h=0;h<t;h++){const a=s[h];let l,u,c=-1,d=0;for(;d<a.length&&(n.lastIndex=d,u=n.exec(a),u!==null);)d=n.lastIndex,n===W?u[1]==="!--"?n=Ae:u[1]!==void 0?n=xe:u[2]!==void 0?(Te.test(u[2])&&(i=RegExp("</"+u[2],"g")),n=j):u[3]!==void 0&&(n=j):n===j?u[0]===">"?(n=i??W,c=-1):u[1]===void 0?c=-2:(c=n.lastIndex-u[2].length,l=u[1],n=u[3]===void 0?j:u[3]==='"'?Se:Ee):n===Se||n===Ee?n=j:n===Ae||n===xe?n=W:(n=j,i=void 0);const v=n===j&&s[h+1].startsWith("/>")?" ":"";o+=n===W?a+Ye:c>=0?(r.push(l),a.slice(0,c)+je+a.slice(c)+z+v):a+z+(c===-2?h:v)}return[Le(s,o+(s[t]||"<?>")+(e===2?"</svg>":e===3?"</math>":"")),r]};class V{constructor({strings:e,_$litType$:t},r){let i;this.parts=[];let o=0,n=0;const h=e.length-1,a=this.parts,[l,u]=tt(e,t);if(this.el=V.createElement(l,r),T.currentNode=this.el.content,t===2||t===3){const c=this.el.content.firstChild;c.replaceWith(...c.childNodes)}for(;(i=T.nextNode())!==null&&a.length<h;){if(i.nodeType===1){if(i.hasAttributes())for(const c of i.getAttributeNames())if(c.endsWith(je)){const d=u[n++],v=i.getAttribute(c).split(z),g=/([.?@])?(.*)/.exec(d);a.push({type:1,index:o,name:g[2],strings:v,ctor:g[1]==="."?it:g[1]==="?"?rt:g[1]==="@"?ot:oe}),i.removeAttribute(c)}else c.startsWith(z)&&(a.push({type:6,index:o}),i.removeAttribute(c));if(Te.test(i.tagName)){const c=i.textContent.split(z),d=c.length-1;if(d>0){i.textContent=ie?ie.emptyScript:"";for(let v=0;v<d;v++)i.append(c[v],Q()),T.nextNode(),a.push({type:2,index:++o});i.append(c[d],Q())}}}else if(i.nodeType===8)if(i.data===Re)a.push({type:2,index:o});else{let c=-1;for(;(c=i.data.indexOf(z,c+1))!==-1;)a.push({type:7,index:o}),c+=z.length-1}o++}}static createElement(e,t){const r=N.createElement("template");return r.innerHTML=e,r}}function I(s,e,t=s,r){var n,h;if(e===U)return e;let i=r!==void 0?(n=t._$Co)==null?void 0:n[r]:t._$Cl;const o=G(e)?void 0:e._$litDirective$;return(i==null?void 0:i.constructor)!==o&&((h=i==null?void 0:i._$AO)==null||h.call(i,!1),o===void 0?i=void 0:(i=new o(s),i._$AT(s,t,r)),r!==void 0?(t._$Co??(t._$Co=[]))[r]=i:t._$Cl=i),i!==void 0&&(e=I(s,i._$AS(s,e.values),i,r)),e}let st=class{constructor(e,t){this._$AV=[],this._$AN=void 0,this._$AD=e,this._$AM=t}get parentNode(){return this._$AM.parentNode}get _$AU(){return this._$AM._$AU}u(e){const{el:{content:t},parts:r}=this._$AD,i=((e==null?void 0:e.creationScope)??N).importNode(t,!0);T.currentNode=i;let o=T.nextNode(),n=0,h=0,a=r[0];for(;a!==void 0;){if(n===a.index){let l;a.type===2?l=new q(o,o.nextSibling,this,e):a.type===1?l=new a.ctor(o,a.name,a.strings,this,e):a.type===6&&(l=new nt(o,this,e)),this._$AV.push(l),a=r[++h]}n!==(a==null?void 0:a.index)&&(o=T.nextNode(),n++)}return T.currentNode=N,i}p(e){let t=0;for(const r of this._$AV)r!==void 0&&(r.strings!==void 0?(r._$AI(e,r,t),t+=r.strings.length-2):r._$AI(e[t])),t++}};class q{get _$AU(){var e;return((e=this._$AM)==null?void 0:e._$AU)??this._$Cv}constructor(e,t,r,i){this.type=2,this._$AH=x,this._$AN=void 0,this._$AA=e,this._$AB=t,this._$AM=r,this.options=i,this._$Cv=(i==null?void 0:i.isConnected)??!0}get parentNode(){let e=this._$AA.parentNode;const t=this._$AM;return t!==void 0&&(e==null?void 0:e.nodeType)===11&&(e=t.parentNode),e}get startNode(){return this._$AA}get endNode(){return this._$AB}_$AI(e,t=this){e=I(this,e,t),G(e)?e===x||e==null||e===""?(this._$AH!==x&&this._$AR(),this._$AH=x):e!==this._$AH&&e!==U&&this._(e):e._$litType$!==void 0?this.$(e):e.nodeType!==void 0?this.T(e):Fe(e)?this.k(e):this._(e)}O(e){return this._$AA.parentNode.insertBefore(e,this._$AB)}T(e){this._$AH!==e&&(this._$AR(),this._$AH=this.O(e))}_(e){this._$AH!==x&&G(this._$AH)?this._$AA.nextSibling.data=e:this.T(N.createTextNode(e)),this._$AH=e}$(e){var o;const{values:t,_$litType$:r}=e,i=typeof r=="number"?this._$AC(e):(r.el===void 0&&(r.el=V.createElement(Le(r.h,r.h[0]),this.options)),r);if(((o=this._$AH)==null?void 0:o._$AD)===i)this._$AH.p(t);else{const n=new st(i,this),h=n.u(this.options);n.p(t),this.T(h),this._$AH=n}}_$AC(e){let t=ke.get(e.strings);return t===void 0&&ke.set(e.strings,t=new V(e)),t}k(e){me(this._$AH)||(this._$AH=[],this._$AR());const t=this._$AH;let r,i=0;for(const o of e)i===t.length?t.push(r=new q(this.O(Q()),this.O(Q()),this,this.options)):r=t[i],r._$AI(o),i++;i<t.length&&(this._$AR(r&&r._$AB.nextSibling,i),t.length=i)}_$AR(e=this._$AA.nextSibling,t){var r;for((r=this._$AP)==null?void 0:r.call(this,!1,!0,t);e&&e!==this._$AB;){const i=e.nextSibling;e.remove(),e=i}}setConnected(e){var t;this._$AM===void 0&&(this._$Cv=e,(t=this._$AP)==null||t.call(this,e))}}class oe{get tagName(){return this.element.tagName}get _$AU(){return this._$AM._$AU}constructor(e,t,r,i,o){this.type=1,this._$AH=x,this._$AN=void 0,this.element=e,this.name=t,this._$AM=i,this.options=o,r.length>2||r[0]!==""||r[1]!==""?(this._$AH=Array(r.length-1).fill(new String),this.strings=r):this._$AH=x}_$AI(e,t=this,r,i){const o=this.strings;let n=!1;if(o===void 0)e=I(this,e,t,0),n=!G(e)||e!==this._$AH&&e!==U,n&&(this._$AH=e);else{const h=e;let a,l;for(e=o[0],a=0;a<o.length-1;a++)l=I(this,h[r+a],t,a),l===U&&(l=this._$AH[a]),n||(n=!G(l)||l!==this._$AH[a]),l===x?e=x:e!==x&&(e+=(l??"")+o[a+1]),this._$AH[a]=l}n&&!i&&this.j(e)}j(e){e===x?this.element.removeAttribute(this.name):this.element.setAttribute(this.name,e??"")}}class it extends oe{constructor(){super(...arguments),this.type=3}j(e){this.element[this.name]=e===x?void 0:e}}class rt extends oe{constructor(){super(...arguments),this.type=4}j(e){this.element.toggleAttribute(this.name,!!e&&e!==x)}}class ot extends oe{constructor(e,t,r,i,o){super(e,t,r,i,o),this.type=5}_$AI(e,t=this){if((e=I(this,e,t,0)??x)===U)return;const r=this._$AH,i=e===x&&r!==x||e.capture!==r.capture||e.once!==r.once||e.passive!==r.passive,o=e!==x&&(r===x||i);i&&this.element.removeEventListener(this.name,this,r),o&&this.element.addEventListener(this.name,this,e),this._$AH=e}handleEvent(e){var t;typeof this._$AH=="function"?this._$AH.call(((t=this.options)==null?void 0:t.host)??this.element,e):this._$AH.handleEvent(e)}}class nt{constructor(e,t,r){this.element=e,this.type=6,this._$AN=void 0,this._$AM=t,this.options=r}get _$AU(){return this._$AM._$AU}_$AI(e){I(this,e)}}const at={I:q},le=K.litHtmlPolyfillSupport;le==null||le(V,q),(K.litHtmlVersions??(K.litHtmlVersions=[])).push("3.3.0");const ct=(s,e,t)=>{const r=(t==null?void 0:t.renderBefore)??e;let i=r._$litPart$;if(i===void 0){const o=(t==null?void 0:t.renderBefore)??null;r._$litPart$=i=new q(e.insertBefore(Q(),o),o,void 0,t??{})}return i._$AI(s),i};/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const L=globalThis;let C=class extends B{constructor(){super(...arguments),this.renderOptions={host:this},this._$Do=void 0}createRenderRoot(){var t;const e=super.createRenderRoot();return(t=this.renderOptions).renderBefore??(t.renderBefore=e.firstChild),e}update(e){const t=this.render();this.hasUpdated||(this.renderOptions.isConnected=this.isConnected),super.update(e),this._$Do=ct(t,this.renderRoot,this.renderOptions)}connectedCallback(){var e;super.connectedCallback(),(e=this._$Do)==null||e.setConnected(!0)}disconnectedCallback(){var e;super.disconnectedCallback(),(e=this._$Do)==null||e.setConnected(!1)}render(){return U}};var Ue;C._$litElement$=!0,C.finalized=!0,(Ue=L.litElementHydrateSupport)==null||Ue.call(L,{LitElement:C});const de=L.litElementPolyfillSupport;de==null||de({LitElement:C});(L.litElementVersions??(L.litElementVersions=[])).push("4.2.0");/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const F=s=>(e,t)=>{t!==void 0?t.addInitializer(()=>{customElements.define(s,e)}):customElements.define(s,e)};/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const lt={attribute:!0,type:String,converter:se,reflect:!1,hasChanged:ve},dt=(s=lt,e,t)=>{const{kind:r,metadata:i}=t;let o=globalThis.litPropertyMetadata.get(i);if(o===void 0&&globalThis.litPropertyMetadata.set(i,o=new Map),r==="setter"&&((s=Object.create(s)).wrapped=!0),o.set(t.name,s),r==="accessor"){const{name:n}=t;return{set(h){const a=e.get.call(this);e.set.call(this,h),this.requestUpdate(n,a,s)},init(h){return h!==void 0&&this.C(n,void 0,s,h),h}}}if(r==="setter"){const{name:n}=t;return function(h){const a=this[n];e.call(this,h),this.requestUpdate(n,a,s)}}throw Error("Unsupported decorator location: "+r)};function S(s){return(e,t)=>typeof t=="object"?dt(s,e,t):((r,i,o)=>{const n=i.hasOwnProperty(o);return i.constructor.createProperty(o,r),n?Object.getOwnPropertyDescriptor(i,o):void 0})(s,e,t)}/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */function ht(s){return S({...s,state:!0,attribute:!1})}/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const pt=(s,e,t)=>(t.configurable=!0,t.enumerable=!0,Reflect.decorate&&typeof e!="object"&&Object.defineProperty(s,e,t),t);/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */function ut(s){return(e,t)=>{const{slot:r,selector:i}={},o="slot"+(r?`[name=${r}]`:":not([name])");return pt(e,t,{get(){var a;const n=(a=this.renderRoot)==null?void 0:a.querySelector(o),h=(n==null?void 0:n.assignedElements(s))??[];return i===void 0?h:h.filter(l=>l.matches(i))}})}}var ft=Object.defineProperty,vt=Object.getOwnPropertyDescriptor,Ne=(s,e,t,r)=>{for(var i=r>1?void 0:r?vt(e,t):e,o=s.length-1,n;o>=0;o--)(n=s[o])&&(i=(r?n(e,t,i):n(i))||i);return r&&i&&ft(e,t,i),i};let re=class extends C{updated(){if(this.items.forEach(s=>{s.classList.remove("first","last"),s.style.removeProperty("--radius"),s.style.removeProperty("--radius-hover")}),this.items.length>0){const s=this.items[0];s.classList.add("first"),s.style.setProperty("--radius","var(--radius-2) var(--radius-2) 0 0"),s.style.setProperty("--radius-hover","var(--radius-2) var(--radius-2) var(--radius-2) 0")}if(this.items.length>1){const s=this.items[this.items.length-1];s.classList.add("last"),s.style.setProperty("--radius","0 0 var(--radius-2) var(--radius-2)"),s.style.setProperty("--radius-hover","0 var(--radius-2) var(--radius-2) var(--radius-2)")}}render(){return E`<slot></slot>`}};re.styles=Y`
    :host {
      height: fit-content;
      display: flex;
      flex-direction: column;
      align-items: center;
    }
  `;Ne([ut()],re.prototype,"items",2);re=Ne([F("category-elem")],re);/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const He={ATTRIBUTE:1,CHILD:2},Be=s=>(...e)=>({_$litDirective$:s,values:e});let Ie=class{constructor(e){}get _$AU(){return this._$AM._$AU}_$AT(e,t,r){this._$Ct=e,this._$AM=t,this._$Ci=r}_$AS(e,t){return this.update(e,t)}update(e,t){return this.render(...t)}};/**
 * @license
 * Copyright 2018 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const De="important",mt=" !"+De,gt=Be(class extends Ie{constructor(s){var e;if(super(s),s.type!==He.ATTRIBUTE||s.name!=="style"||((e=s.strings)==null?void 0:e.length)>2)throw Error("The `styleMap` directive must be used in the `style` attribute and must be the only part in the attribute.")}render(s){return Object.keys(s).reduce((e,t)=>{const r=s[t];return r==null?e:e+`${t=t.includes("-")?t:t.replace(/(?:^(webkit|moz|ms|o)|)(?=[A-Z])/g,"-$&").toLowerCase()}:${r};`},"")}update(s,[e]){const{style:t}=s.element;if(this.ft===void 0)return this.ft=new Set(Object.keys(e)),this.render(e);for(const r of this.ft)e[r]==null&&(this.ft.delete(r),r.includes("-")?t.removeProperty(r):t[r]=null);for(const r in e){const i=e[r];if(i!=null){this.ft.add(r);const o=typeof i=="string"&&i.endsWith(mt);r.includes("-")||o?t.setProperty(r,o?i.slice(0,-11):i,o?De:""):t[r]=i}}return U}});var $t=Object.defineProperty,yt=Object.getOwnPropertyDescriptor,ee=(s,e,t,r)=>{for(var i=r>1?void 0:r?yt(e,t):e,o=s.length-1,n;o>=0;o--)(n=s[o])&&(i=(r?n(e,t,i):n(i))||i);return r&&i&&$t(e,t,i),i};let H=class extends C{constructor(){super(...arguments),this.href="#",this.value="",this.bgColor="white",this.textColor="black"}render(){const s={first:this.classList.contains("first"),last:this.classList.contains("last")},e=Object.entries(s).filter(([,t])=>t).map(([t])=>t).join(" ");return E`
      <a
        part="item"
        href="${this.href}"
        class="${e}"
        style=${gt({"--hover-bg-color":this.bgColor,"--hover-color":this.textColor})}
      >
        <span class="icon"><slot></slot></span>
        <span class="label">${this.value}</span>
      </a>
    `}};H.styles=Y`
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
  `;ee([S({type:String})],H.prototype,"href",2);ee([S({type:String})],H.prototype,"value",2);ee([S({type:String})],H.prototype,"bgColor",2);ee([S({type:String})],H.prototype,"textColor",2);H=ee([F("category-item")],H);var bt=Object.defineProperty,_t=Object.getOwnPropertyDescriptor,M=(s,e,t,r)=>{for(var i=r>1?void 0:r?_t(e,t):e,o=s.length-1,n;o>=0;o--)(n=s[o])&&(i=(r?n(e,t,i):n(i))||i);return r&&i&&bt(e,t,i),i};let P=class extends C{constructor(){super(...arguments),this.pId=0,this.pName="",this.src="",this.desc="",this.pReg="",this.prices=null,this.checkedIds=new Set}render(){var s;return E`
            <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
            <div class="p-item">
                <a href="#" class="p-img-container">
                    <img src=${this.src} alt="" class="p-img" loading="lazy">
                </a>
                <div class="p-content">
                    <a class="p-name" href="#">${this.pName}</a>
                    <div class="p-desc">${this.desc}</div>
                    <div class="p-reg">등록일 ${this.pReg}</div>
                </div>
                <div class="p-prices">
                    ${(s=this.prices)==null?void 0:s.map(e=>this.priceRender(e))}
                </div>
            </div>
        `}priceRender(s){const e=this.checkedIds.has(s.id),t=E`
            <svg xmlns="http://www.w3.org/2000/svg" enable-background="new 0 0 24 24" viewBox="0 0 24 24" fill="currentColor"><rect fill="none" height="24" width="24"/><path d="M3,5v14c0,1.1,0.9,2,2,2h14c1.1,0,2-0.9,2-2V5c0-1.1-0.9-2-2-2H5C3.9,3,3,3.9,3,5z M16.3,16.29L16.3,16.29 c-0.39,0.39-1.02,0.39-1.41,0L12,13.41l-2.89,2.89c-0.39,0.39-1.02,0.39-1.41,0l0,0c-0.39-0.39-0.39-1.02,0-1.41L10.59,12L7.7,9.11 c-0.39-0.39-0.39-1.02,0-1.41l0,0c0.39-0.39,1.02-0.39,1.41,0L12,10.59l2.89-2.88c0.39-0.39,1.02-0.39,1.41,0l0,0 c0.39,0.39,0.39,1.02,0,1.41L13.41,12l2.89,2.88C16.68,15.27,16.68,15.91,16.3,16.29z"/></svg>
        `;return s.schedule?E`
            <div class="price-item">
                <span class="price-name">${s.name}</span>
                <span class="price-value">가격비교예정</span>
                <span class="material-icons price-plus disabled">add_box</span>
            </div>
        `:E`
            <div class="price-item">
                <span class="price-name">${s.name}</span>
                <span class="price-value">${s.price.toLocaleString()}원</span>
                <span class="material-icons price-plus ${e&&"checked"}" @click=${()=>this.togglePriceCheck(s)}>
                    ${e?t:"add_box"}
                </span>
            </div>
        `}dispatchSelect(s){const e={...s,name:this.pName+" "+s.name};this.dispatchEvent(new CustomEvent("select",{detail:{product:e},bubbles:!0,composed:!0}))}togglePriceCheck(s){const e=s.id;this.checkedIds.has(e)?this.checkedIds.delete(e):this.checkedIds.add(e),this.requestUpdate();const t={...s,name:`${this.pName} ${s.name}`};this.dispatchEvent(new CustomEvent("select",{detail:{product:t,checked:this.checkedIds.has(e)},bubbles:!0,composed:!0}))}setChecked(s,e){e?this.checkedIds.add(s):this.checkedIds.delete(s),this.requestUpdate()}};P.styles=Y`
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
                }
                & .price-value {
                    flex: 0;
                    text-align: right;
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
    `;M([S({type:Number})],P.prototype,"pId",2);M([S({type:String})],P.prototype,"pName",2);M([S({type:String})],P.prototype,"src",2);M([S({type:String})],P.prototype,"desc",2);M([S({type:String})],P.prototype,"pReg",2);M([S({type:Array})],P.prototype,"prices",2);M([ht()],P.prototype,"checkedIds",2);P=M([F("product-item")],P);/**
 * @license
 * Copyright 2020 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const{I:wt}=at,Pe=()=>document.createComment(""),Z=(s,e,t)=>{var o;const r=s._$AA.parentNode,i=e===void 0?s._$AB:e._$AA;if(t===void 0){const n=r.insertBefore(Pe(),i),h=r.insertBefore(Pe(),i);t=new wt(n,h,s,s.options)}else{const n=t._$AB.nextSibling,h=t._$AM,a=h!==s;if(a){let l;(o=t._$AQ)==null||o.call(t,s),t._$AM=s,t._$AP!==void 0&&(l=s._$AU)!==h._$AU&&t._$AP(l)}if(n!==i||a){let l=t._$AA;for(;l!==n;){const u=l.nextSibling;r.insertBefore(l,i),l=u}}}return t},R=(s,e,t=s)=>(s._$AI(e,t),s),At={},xt=(s,e=At)=>s._$AH=e,Et=s=>s._$AH,he=s=>{var r;(r=s._$AP)==null||r.call(s,!1,!0);let e=s._$AA;const t=s._$AB.nextSibling;for(;e!==t;){const i=e.nextSibling;e.remove(),e=i}};/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const Ce=(s,e,t)=>{const r=new Map;for(let i=e;i<=t;i++)r.set(s[i],i);return r},St=Be(class extends Ie{constructor(s){if(super(s),s.type!==He.CHILD)throw Error("repeat() can only be used in text expressions")}dt(s,e,t){let r;t===void 0?t=e:e!==void 0&&(r=e);const i=[],o=[];let n=0;for(const h of s)i[n]=r?r(h,n):n,o[n]=t(h,n),n++;return{values:o,keys:i}}render(s,e,t){return this.dt(s,e,t).values}update(s,[e,t,r]){const i=Et(s),{values:o,keys:n}=this.dt(e,t,r);if(!Array.isArray(i))return this.ut=n,o;const h=this.ut??(this.ut=[]),a=[];let l,u,c=0,d=i.length-1,v=0,g=o.length-1;for(;c<=d&&v<=g;)if(i[c]===null)c++;else if(i[d]===null)d--;else if(h[c]===n[v])a[v]=R(i[c],o[v]),c++,v++;else if(h[d]===n[g])a[g]=R(i[d],o[g]),d--,g--;else if(h[c]===n[g])a[g]=R(i[c],o[g]),Z(s,a[g+1],i[c]),c++,g--;else if(h[d]===n[v])a[v]=R(i[d],o[v]),Z(s,i[c],i[d]),d--,v++;else if(l===void 0&&(l=Ce(n,v,g),u=Ce(h,c,d)),l.has(h[c]))if(l.has(h[d])){const _=u.get(n[v]),k=_!==void 0?i[_]:null;if(k===null){const b=Z(s,i[c]);R(b,o[v]),a[v]=b}else a[v]=R(k,o[v]),Z(s,i[c],k),i[_]=null;v++}else he(i[d]),d--;else he(i[c]),c++;for(;v<=g;){const _=Z(s,a[g+1]);R(_,o[v]),a[v++]=_}for(;c<=d;){const _=i[c++];_!==null&&he(_)}return this.ut=n,xt(s,a),U}});var kt=Object.defineProperty,Pt=Object.getOwnPropertyDescriptor,ne=(s,e,t,r)=>{for(var i=r>1?void 0:r?Pt(e,t):e,o=s.length-1,n;o>=0;o--)(n=s[o])&&(i=(r?n(e,t,i):n(i))||i);return r&&i&&kt(e,t,i),i};let D=class extends C{constructor(){super(...arguments),this.products=[]}firstUpdated(s){this.addEventListener("select",this.handleSelect)}updated(s){s.has("basketSideEl")&&this.basketSideEl&&this.basketSideEl.addEventListener("remove",e=>this.onBasketRemove(e))}setProducts(s){this.products=s}updateProductById(s,e){this.products=this.products.map(t=>t.id===s?{...t,...e}:t)}setChecked(s,e,t){const r=this.renderRoot.querySelectorAll("product-item");for(const i of r)if(i.pId===s){i.setChecked(e,t);break}}handleSelect(s){if(!this.basketSideEl)return;const e=s.detail.product,t=s.detail.checked;if(!this.category)return;const r=this.basketSideEl;r&&(t?r.addProduct(this.category,e.id,e.name||"",e.price):r.removeProduct(this.category,e.id))}onBasketRemove(s){if(!this.basketSideEl)return;s.detail.product.forEach(t=>{const r=Array.from(this.renderRoot.querySelectorAll("product-item")).find(i=>{var n;return(n=i.prices)==null?void 0:n.some(h=>h.id===t.id)});r?r.setChecked(t.id,!1):console.warn("상품을 찾을 수 없습니다 (priceId):",t.id)})}render(){return E`
            <div id="product-list">
                ${St(this.products,s=>s.id,s=>E`
                  <product-item
                          .pId=${s.id}
                          .pName=${s.name}
                          .src=${s.src}
                          .desc=${s.desc}
                          .pReg=${s.pReg}
                          .prices=${s.prices}
                  ></product-item>
                `)}
            </div>
        `}};D.styles=Y`
        #product-list {
            display: flex;
            flex-direction: column;
            gap: var(--size-1);
        }
    `;ne([S({type:String})],D.prototype,"category",2);ne([S({type:Object})],D.prototype,"basketSideEl",2);ne([S({type:Array})],D.prototype,"products",2);D=ne([F("product-list")],D);function Ct(s){return s&&s.__esModule&&Object.prototype.hasOwnProperty.call(s,"default")?s.default:s}var pe={exports:{}},ze;function zt(){return ze||(ze=1,function(s){var e=function(){var t=String.fromCharCode,r="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",i="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-$",o={};function n(a,l){if(!o[a]){o[a]={};for(var u=0;u<a.length;u++)o[a][a.charAt(u)]=u}return o[a][l]}var h={compressToBase64:function(a){if(a==null)return"";var l=h._compress(a,6,function(u){return r.charAt(u)});switch(l.length%4){default:case 0:return l;case 1:return l+"===";case 2:return l+"==";case 3:return l+"="}},decompressFromBase64:function(a){return a==null?"":a==""?null:h._decompress(a.length,32,function(l){return n(r,a.charAt(l))})},compressToUTF16:function(a){return a==null?"":h._compress(a,15,function(l){return t(l+32)})+" "},decompressFromUTF16:function(a){return a==null?"":a==""?null:h._decompress(a.length,16384,function(l){return a.charCodeAt(l)-32})},compressToUint8Array:function(a){for(var l=h.compress(a),u=new Uint8Array(l.length*2),c=0,d=l.length;c<d;c++){var v=l.charCodeAt(c);u[c*2]=v>>>8,u[c*2+1]=v%256}return u},decompressFromUint8Array:function(a){if(a==null)return h.decompress(a);for(var l=new Array(a.length/2),u=0,c=l.length;u<c;u++)l[u]=a[u*2]*256+a[u*2+1];var d=[];return l.forEach(function(v){d.push(t(v))}),h.decompress(d.join(""))},compressToEncodedURIComponent:function(a){return a==null?"":h._compress(a,6,function(l){return i.charAt(l)})},decompressFromEncodedURIComponent:function(a){return a==null?"":a==""?null:(a=a.replace(/ /g,"+"),h._decompress(a.length,32,function(l){return n(i,a.charAt(l))}))},compress:function(a){return h._compress(a,16,function(l){return t(l)})},_compress:function(a,l,u){if(a==null)return"";var c,d,v={},g={},_="",k="",b="",A=2,w=3,$=2,y=[],p=0,m=0,f;for(f=0;f<a.length;f+=1)if(_=a.charAt(f),Object.prototype.hasOwnProperty.call(v,_)||(v[_]=w++,g[_]=!0),k=b+_,Object.prototype.hasOwnProperty.call(v,k))b=k;else{if(Object.prototype.hasOwnProperty.call(g,b)){if(b.charCodeAt(0)<256){for(c=0;c<$;c++)p=p<<1,m==l-1?(m=0,y.push(u(p)),p=0):m++;for(d=b.charCodeAt(0),c=0;c<8;c++)p=p<<1|d&1,m==l-1?(m=0,y.push(u(p)),p=0):m++,d=d>>1}else{for(d=1,c=0;c<$;c++)p=p<<1|d,m==l-1?(m=0,y.push(u(p)),p=0):m++,d=0;for(d=b.charCodeAt(0),c=0;c<16;c++)p=p<<1|d&1,m==l-1?(m=0,y.push(u(p)),p=0):m++,d=d>>1}A--,A==0&&(A=Math.pow(2,$),$++),delete g[b]}else for(d=v[b],c=0;c<$;c++)p=p<<1|d&1,m==l-1?(m=0,y.push(u(p)),p=0):m++,d=d>>1;A--,A==0&&(A=Math.pow(2,$),$++),v[k]=w++,b=String(_)}if(b!==""){if(Object.prototype.hasOwnProperty.call(g,b)){if(b.charCodeAt(0)<256){for(c=0;c<$;c++)p=p<<1,m==l-1?(m=0,y.push(u(p)),p=0):m++;for(d=b.charCodeAt(0),c=0;c<8;c++)p=p<<1|d&1,m==l-1?(m=0,y.push(u(p)),p=0):m++,d=d>>1}else{for(d=1,c=0;c<$;c++)p=p<<1|d,m==l-1?(m=0,y.push(u(p)),p=0):m++,d=0;for(d=b.charCodeAt(0),c=0;c<16;c++)p=p<<1|d&1,m==l-1?(m=0,y.push(u(p)),p=0):m++,d=d>>1}A--,A==0&&(A=Math.pow(2,$),$++),delete g[b]}else for(d=v[b],c=0;c<$;c++)p=p<<1|d&1,m==l-1?(m=0,y.push(u(p)),p=0):m++,d=d>>1;A--,A==0&&(A=Math.pow(2,$),$++)}for(d=2,c=0;c<$;c++)p=p<<1|d&1,m==l-1?(m=0,y.push(u(p)),p=0):m++,d=d>>1;for(;;)if(p=p<<1,m==l-1){y.push(u(p));break}else m++;return y.join("")},decompress:function(a){return a==null?"":a==""?null:h._decompress(a.length,32768,function(l){return a.charCodeAt(l)})},_decompress:function(a,l,u){var c=[],d=4,v=4,g=3,_="",k=[],b,A,w,$,y,p,m,f={val:u(0),position:l,index:1};for(b=0;b<3;b+=1)c[b]=b;for(w=0,y=Math.pow(2,2),p=1;p!=y;)$=f.val&f.position,f.position>>=1,f.position==0&&(f.position=l,f.val=u(f.index++)),w|=($>0?1:0)*p,p<<=1;switch(w){case 0:for(w=0,y=Math.pow(2,8),p=1;p!=y;)$=f.val&f.position,f.position>>=1,f.position==0&&(f.position=l,f.val=u(f.index++)),w|=($>0?1:0)*p,p<<=1;m=t(w);break;case 1:for(w=0,y=Math.pow(2,16),p=1;p!=y;)$=f.val&f.position,f.position>>=1,f.position==0&&(f.position=l,f.val=u(f.index++)),w|=($>0?1:0)*p,p<<=1;m=t(w);break;case 2:return""}for(c[3]=m,A=m,k.push(m);;){if(f.index>a)return"";for(w=0,y=Math.pow(2,g),p=1;p!=y;)$=f.val&f.position,f.position>>=1,f.position==0&&(f.position=l,f.val=u(f.index++)),w|=($>0?1:0)*p,p<<=1;switch(m=w){case 0:for(w=0,y=Math.pow(2,8),p=1;p!=y;)$=f.val&f.position,f.position>>=1,f.position==0&&(f.position=l,f.val=u(f.index++)),w|=($>0?1:0)*p,p<<=1;c[v++]=t(w),m=v-1,d--;break;case 1:for(w=0,y=Math.pow(2,16),p=1;p!=y;)$=f.val&f.position,f.position>>=1,f.position==0&&(f.position=l,f.val=u(f.index++)),w|=($>0?1:0)*p,p<<=1;c[v++]=t(w),m=v-1,d--;break;case 2:return k.join("")}if(d==0&&(d=Math.pow(2,g),g++),c[m])_=c[m];else if(m===v)_=A+A.charAt(0);else return null;k.push(_),c[v++]=A+_.charAt(0),d--,A=_,d==0&&(d=Math.pow(2,g),g++)}}};return h}();s!=null?s.exports=e:typeof angular<"u"&&angular!=null&&angular.module("LZString",[]).factory("LZString",function(){return e})}(pe)),pe.exports}var Ot=zt();const Oe=Ct(Ot);var Ut=Object.defineProperty,Mt=Object.getOwnPropertyDescriptor,ge=(s,e,t,r)=>{for(var i=r>1?void 0:r?Mt(e,t):e,o=s.length-1,n;o>=0;o--)(n=s[o])&&(i=(r?n(e,t,i):n(i))||i);return r&&i&&Ut(e,t,i),i};let X=class extends C{constructor(){super(...arguments),this.category=[],this.basket=new Map}addProduct(s,e,t,r){let i=[];if(this.basket.has(s)&&(i=this.basket.get(s)),!i.some(n=>n.id===e))i.push({id:e,name:t,price:r,amount:1});else{let n=this.getProductAmount(i,e);n!=null&&(n++,i=this.updateAmountImmutable(i,e,n))}this.basket.set(s,i),this.requestUpdate()}minusProduct(s,e){let t=[];if(this.basket.has(s)&&(t=this.basket.get(s)),t.some(i=>i.id===e)){let i=this.getProductAmount(t,e);if(i)if(i>1)i--,this.updateAmountImmutable(t,e,i),this.dispatchChange();else{const o=this.findById(t,e);t=this.removeById(t,e),o&&this.dispatchRemove(o)}}this.basket.set(s,t),this.requestUpdate()}removeProduct(s,e){let t=[];if(this.basket.has(s)&&(t=this.basket.get(s)),t.some(i=>i.id===e)){const i=this.findById(t,e);t=this.removeById(t,e),i&&this.dispatchRemove(i)}if(t.length==0){this.basket.delete(s),this.requestUpdate();return}this.basket.set(s,t),this.requestUpdate()}productClear(){this.dispatchRemoves(),this.basket.clear(),this.requestUpdate()}getProductAmount(s,e){const t=s.find(r=>r.id===e);return t!=null?t.amount:null}updateAmountImmutable(s,e,t){return s.map(r=>r.id===e?{...r,amount:t}:r)}findById(s,e){return s.find(t=>t.id===e)}removeById(s,e){return s.filter(t=>t.id!==e)}get calculateTotalPrice(){let s=0;for(const[,e]of this.basket)for(const t of e)s+=t.price*t.amount;return s}get compressedData(){const s=Array.from(this.basket.entries()),e=JSON.stringify(s);return Oe.compressToBase64(e)}set decompressedData(s){const e=Oe.decompressFromBase64(s);if(!e)throw new Error("압축 해제 실패");const t=JSON.parse(e);this.basket=new Map(Object.entries(t))}dispatchChange(){const s=this.basket;this.dispatchEvent(new CustomEvent("change",{detail:{basket:s},bubbles:!0,composed:!0}))}dispatchRemove(s){const e={id:s.id,price:s.price,name:s.name,schedule:!0};this.dispatchEvent(new CustomEvent("remove",{detail:{product:[e]},bubbles:!0,composed:!0}))}dispatchRemoves(){const s=Array.from(this.basket.values()).flat().map(e=>({id:e.id,name:e.name,price:e.price,schedule:!0}));this.dispatchEvent(new CustomEvent("remove",{detail:{product:s}}))}handleInput(s,e,t){const r=s.target,i=Number(r.value);if(i<1||isNaN(i))return;const o=this.basket.get(e);if(!o)return;const n=o.map(h=>h.id===t?{...h,amount:i}:h);this.basket.set(e,n),this.requestUpdate()}basketContentRenderer(){return this.basket.size===0?E`
      <div id="basket-empty">
        비어있습니다
      </div>
    `:this.category.length>0&&this.category.some(e=>this.basket.has(e.code))?E`
      ${this.category.filter(e=>this.basket.has(e.code)).sort((e,t)=>e.id-t.id).map(e=>{const t=this.basket.get(e.code);return E`
            <div class="basket-category">${e.name}</div>
            ${t.map(r=>E`
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
    `:E`
    ${Array.from(this.basket.entries()).map(([e,t])=>E`
        <div class="basket-category">${e.toUpperCase()}</div>
        ${t.map(r=>E`
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
  `}render(){return E`
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
    `}};X.styles=Y`
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
  `;ge([S({type:Array})],X.prototype,"category",2);ge([S({type:Map})],X.prototype,"basket",2);X=ge([F("basket-side")],X);
