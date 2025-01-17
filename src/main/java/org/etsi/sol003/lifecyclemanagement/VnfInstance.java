package org.etsi.sol003.lifecyclemanagement;

import java.util.List;
import java.util.Map;

import org.etsi.sol003.common.Link;
import org.etsi.sol003.common.VimConnectionInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Represents a VNF instance
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Represents a VNF instance")
public class VnfInstance {

    @ApiModelProperty(name = "Id", required = true, notes = "Identifier of the VNF instance.")
    private String id;
    @ApiModelProperty(name = "VNF Instance Name", notes = "Name of the VNF instance.")
    private String vnfInstanceName;
    @ApiModelProperty(name = "VNF Instance Description", notes = "Human-readable description of the VNF instance.")
    private String vnfInstanceDescription;
    @ApiModelProperty(name = "VNFD Id", required = true, notes = "Identifier of the VNFD on which the VNF instance is based.")
    private String vnfdId;
    @ApiModelProperty(name = "VNF Provider", required = true, notes = "Provider of the VNF and the VNFD. The value is copied from the VNFD.")
    private String vnfProvider;
    @ApiModelProperty(name = "VNF Product Name", required = true, notes = "Name to identify the VNF Product. The value is copied from the VNFD.")
    private String vnfProductName;
    @ApiModelProperty(name = "VNF Software Version", required = true, notes = "Software version of the VNF. The value is copied from the VNFD.")
    private String vnfSoftwareVersion;
    @ApiModelProperty(name = "VNFD Version", required = true, notes = "Identifies the version of the VNFD. The value is copied from the VNFD.")
    private String vnfdVersion;
    @ApiModelProperty(name = "Onboarded VNF Package Information Id", required = true, notes = "Identifier of information held by the NFVO about the specific VNF package on which the VNF is based.")
    private String vnfPkgId;
    @ApiModelProperty(name = "VNF Configurable Properties", notes = "Current values of the configurable properties of the VNF instance.")
    private Map<String, String> vnfConfigurableProperties;
    @ApiModelProperty(name = "VIM Connection Information", notes = "Information about VIM connections to be used for managing the resources for the VNF instance. This attribute shall only be supported and present if VNF-related resource management in direct mode is applicable.")
    private Map<String,VimConnectionInfo> vimConnectionInfo;
    @ApiModelProperty(name = "Instantiation State", required = true, notes = "The instantiation state of the VNF.")
    private InstantiationState instantiationState;
    @ApiModelProperty(name = "Instantiated VNF Information", notes = "Information specific to an instantiated VNF instance. This attribute shall be present if the instantiateState attribute value is INSTANTIATED.")
    private InstantiatedVnfInfo instantiatedVnfInfo;
    @ApiModelProperty(name = "Metadata", notes = "Additional VNF-specific metadata describing the VNF instance.")
    private Map<String, String> metadata;
    @ApiModelProperty(name = "Extensions", notes = "VNF-specific attributes that affect the lifecycle management of this VNF instance by the VNFM, or the lifecycle management scripts.")
    private Map<String, String> extensions;
    @ApiModelProperty(name = "Links", required = true, notes = "Links to resources related to this resource.")
    @JsonProperty("_links")
    private Links links;

    @Data
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ApiModel(description = "Information specific to an instantiated VNF instance.")
    public static class InstantiatedVnfInfo {

        @ApiModelProperty(name = "Flavour Id", required = true, notes = "Identifier of the VNF deployment flavour applied to this VNF instance.")
        private String flavourId;
        @ApiModelProperty(name = "VNF Operational State", required = true, notes = "State of the VNF instance.")
        private VnfOperationalStateType vnfState;
        @ApiModelProperty(name = "Scale Status", notes = "Scale status of the VNF, one entry per aspect. Represents for every scaling aspect how \"big\" the VNF has been scaled w.r.t. that aspect.")
        private List<ScaleInfo> scaleStatus;
        @ApiModelProperty(name = "Max Scale Levels", notes = "Maximum allowed scale levels of the VNF, one entry per aspect.")
        private List<ScaleInfo> maxScaleLevels;
        @ApiModelProperty(name = "External Connection Point Information", required = true, notes = "Information about the external CPs exposed by the VNF instance.")
        private List<VnfInstance.ExtCpInfo> extCpInfo;
        @ApiModelProperty(name = "vip CP Info", required = true, notes = "Information about the external CPs exposed by the VNF instance.")
        private List<VnfInstance.VipCpInfo> vipCpInfo;
        @ApiModelProperty(name = "External Virtual Link Information", notes = "Information about the external VLs the VNF instance is connected to.")
        private List<ExtVirtualLinkInfo> extVirtualLinkInfo;
        @ApiModelProperty(name = "External Managed Virtual Link Information", notes = "Information about the externally-managed internal VLs of the VNF instance.")
        private List<ExtManagedVirtualLinkInfo> extManagedVirtualLinkInfo;
        @ApiModelProperty(name = "Monitoring Parameters", notes = "Active monitoring parameters.")
        private List<MonitoringParameter> monitoringParameters;
        @ApiModelProperty(name = "Localization Language", notes = "Information about localization language of the VNF (includes e.g. strings in the VNFD). The localization languages supported by a VNF can be declared in the VNFD, and localization language selection can take place at instantiation time. The value shall comply with the format defined in IETF RFC 5646.")
        private String localizationLanguage;
        @ApiModelProperty(name = "VNFC Resource Information", notes = "Information about the virtualised compute and storage resources used by the VNFCs of the VNF instance.")
        private List<VnfcResourceInfo> vnfcResourceInfo;
        @ApiModelProperty(name = "VNF Virtual Link Resource Information", notes = "Information about the virtualised network resources used by the VLs of the VNF instance.")
        private List<VnfVirtualLinkResourceInfo> vnfVirtualLinkResourceInfo;
        @ApiModelProperty(name = "Virtual Storage Resource Information", notes = "Information about the virtualised storage resources used as storage for the VNF instance.")
        private List<VirtualStorageResourceInfo> virtualStorageResourceInfo;
        @ApiModelProperty(name = "Metadata", notes = "Additional VNF-specific attributes that provide metadata describing the VNF instance.")
        private Map<String, String> metadata;
        @ApiModelProperty(name = "Extensions", notes = "Additional VNF-specific attributes that affect the lifecycle management of this VNF instance.")
        private Map<String, String> extensions;

    }

    @Data
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ApiModel(description = "Information about the external CPs exposed by the VNF instance.")
    public static class ExtCpInfo {

        @ApiModelProperty(name = "External CP Id", required = true, notes = "Identifier of the external CP instance and the related information instance.")
        private String id;
        @ApiModelProperty(name = "External CPD Id", required = true, notes = "Identifier of the external CPD, VnfExtCpd, in the VNFD.")
        private String cpdId;
        @ApiModelProperty(name = "CP Protocol Information", notes = "Network protocol information for this CP.")
        private List<CpProtocolInfo> cpProtocolInfo;
        @ApiModelProperty(name = "External Link Port Id", notes = "Identifier of the \"extLinkPortInfo\" structure inside the \"extVirtualLinkInfo\" structure. Shall be present if the CP is associated to a link port.")
        private String extLinkPortId;

    }

    @Data
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ApiModel(description = "Links to resources related to this resource.")
    public static class Links {

        @ApiModelProperty(name = "self", required = true, notes = "URI of this resource.")
        private Link self;
        @ApiModelProperty(name = "indicators", notes = "Indicators related to this VNF instance, if applicable.")
        private Link indicators;
        @ApiModelProperty(name = "instantiate", notes = "Link to the \"instantiate\" task resource, if the related operation is possible based on the current status of this VNF instance resource (i.e. VNF instance in NOT_INSTANTIATED state).")
        private Link instantiate;
        @ApiModelProperty(name = "terminate", notes = "Link to the \"terminate\" task resource, if the related operation is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state).")
        private Link terminate;
        @ApiModelProperty(name = "scale", notes = "Link to the \"scale\" task resource, if the related operation is supported for this VNF instance, and is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state).")
        private Link scale;
        @ApiModelProperty(name = "scaleToLevel", notes = "Link to the \"scale_to_level\" task resource, if the related operation is supported for this VNF instance, and is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state).")
        private Link scaleToLevel;
        @ApiModelProperty(name = "changeFlavour", notes = "Link to the \"change_flavour\" task resource, if the related operation is supported for this VNF instance, and is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state).")
        private Link changeFlavour;
        @ApiModelProperty(name = "heal", notes = "Link to the \"heal\" task resource, if the related operation is supported for this VNF instance, and is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state).")
        private Link heal;
        @ApiModelProperty(name = "operate", notes = "Link to the \"operate\" task resource, if the related operation is supported for this VNF instance, and is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state).")
        private Link operate;
        @ApiModelProperty(name = "changeExtConn", notes = "Link to the \"change_ext_conn\" task resource, if the related operation is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state).")
        private Link changeExtConn;
        @ApiModelProperty(name = "createSnapshot", notes = "Link to the \"Create VNF snapshot task\" task resource, if the related operation is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state).")
        private Link createSnapshot;
        @ApiModelProperty(name = "revertToSnapshot", notes = "Link to the \"Revert to VNF snapshot task\"  resource, if the related operation is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state).")
        private Link revertToSnapshot;

    }
    @Data
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ApiModel(description = "Information about related to virtual IP (VIP) CP.")
    public static class VipCpInfo {

        @ApiModelProperty(name = "cp Instance Id", required = true, notes = "Identifier of this VIP CP instance and of this VipCpInfo information element.")
        private String cpInstanceId;
        @ApiModelProperty(name = "External CPD Id", required = true, notes = "Identifier of the VIP Connection Point Descriptor, VipCpd, in the VNFD.")
        private String cpdId;
        @ApiModelProperty(name = "vnfExtCpId",notes = "When the VIP CP is exposed as external CP of the VNF, the identifier of this external VNF CP instance.")
        private String vnfExtCpId;
        @ApiModelProperty(name = "CP Protocol Information", notes = "Protocol information for this CP. There shall be one cpProtocolInfo for layer 3. There may be one cpProtocolInfo for layer 2.")
        private List<CpProtocolInfo> cpProtocolInfo;
        @ApiModelProperty(name = "Associated Vnfc CpIds", notes = "Identifiers of the VnfcCps that share the virtual IP addresse allocated to the VIP CP instance.")
        private List<String> associatedVnfcCpIds;
        @ApiModelProperty(name = "VNF  Link Port Id", notes = "Identifier of the \"VnfLinkPortInfo\" structure in the \"VnfVirtualLinkResourceInfo\" or \"ExtManagedVirtualLinkInfo\" structure. Shall be present if the CP is associated to a link port on an internal VL.")
        private String vnfLinkPortId;
        @ApiModelProperty(name = "Metadata", notes = "Metadata about this VIP CP.")
        private Map<String, String> metadata;

    }
}
