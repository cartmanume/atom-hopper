//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.09.29 at 04:22:14 PM CDT 
//


package com.rackspace.cloud.sense.config.v1_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.rackspacecloud.com/cloud-sense/sense-config/v1.0}feed" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="title" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="adapter-ref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="adapter-class" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="resource" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "feed"
})
@XmlRootElement(name = "service")
public class Service {

    protected List<Feed> feed;
    @XmlAttribute(required = true)
    protected String title;
    @XmlAttribute(name = "adapter-ref")
    protected String adapterRef;
    @XmlAttribute(name = "adapter-class")
    protected String adapterClass;
    @XmlAttribute
    protected String resource;

    /**
     * Gets the value of the feed property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the feed property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeed().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Feed }
     * 
     * 
     */
    public List<Feed> getFeed() {
        if (feed == null) {
            feed = new ArrayList<Feed>();
        }
        return this.feed;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the adapterRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdapterRef() {
        return adapterRef;
    }

    /**
     * Sets the value of the adapterRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdapterRef(String value) {
        this.adapterRef = value;
    }

    /**
     * Gets the value of the adapterClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdapterClass() {
        return adapterClass;
    }

    /**
     * Sets the value of the adapterClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdapterClass(String value) {
        this.adapterClass = value;
    }

    /**
     * Gets the value of the resource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResource() {
        return resource;
    }

    /**
     * Sets the value of the resource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResource(String value) {
        this.resource = value;
    }

}
