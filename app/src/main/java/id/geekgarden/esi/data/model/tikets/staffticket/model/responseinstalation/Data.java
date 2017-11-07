
package id.geekgarden.esi.data.model.tikets.staffticket.model.responseinstalation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("customer name")
    @Expose
    private String customerName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("phone number")
    @Expose
    private String phoneNumber;
    @SerializedName("fax number")
    @Expose
    private String faxNumber;
    @SerializedName("grounding")
    @Expose
    private String grounding;
    @SerializedName("easyaccess_key")
    @Expose
    private String easyaccessKey;
    @SerializedName("easyaccess_serial_number")
    @Expose
    private String easyaccessSerialNumber;
    @SerializedName("operating_system")
    @Expose
    private String operatingSystem;
    @SerializedName("os_product_key")
    @Expose
    private String osProductKey;
    @SerializedName("support_id")
    @Expose
    private Integer supportId;
    @SerializedName("support name")
    @Expose
    private String supportName;
    @SerializedName("installation_date")
    @Expose
    private String installationDate;
    @SerializedName("contract_type_id")
    @Expose
    private String contractTypeId;
    @SerializedName("data_updated_on")
    @Expose
    private String dataUpdatedOn;
    @SerializedName("instrument_type_id")
    @Expose
    private String instrumentSerialNumber;
    @SerializedName("instrument_serial_number")
    @Expose
    private String instrumentTypeId;
    @SerializedName("mcp_serial_number")
    @Expose
    private String pneumaticUnitSerialNumber;
    @SerializedName("pneumatic_unit_serial_number")
    @Expose
    private String sampleLoaderSerialNumber;
    @SerializedName("sample_loader_serial_number")
    @Expose
    private String mcpSerialNumber;
    @SerializedName("barcode_reader_serial_number")
    @Expose
    private String barcodeReaderSerialNumber;
    @SerializedName("xt_1800i_xt_2000i_pim")
    @Expose
    private String xt1800iXt2000iPim;
    @SerializedName("cpu_serial_number")
    @Expose
    private String cpuSerialNumber;
    @SerializedName("monitor_serial_number")
    @Expose
    private String monitorSerialNumber;
    @SerializedName("keyboard_serial_number")
    @Expose
    private String keyboardSerialNumber;
    @SerializedName("mouse_serial_number")
    @Expose
    private String mouseSerialNumber;
    @SerializedName("printer_model")
    @Expose
    private String printerModel;
    @SerializedName("printer_serial_number")
    @Expose
    private String printerSerialNumber;
    @SerializedName("ups_model")
    @Expose
    private String upsModel;
    @SerializedName("ups_serial_number")
    @Expose
    private String upsSerialNumber;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getGrounding() {
        return grounding;
    }

    public void setGrounding(String grounding) {
        this.grounding = grounding;
    }

    public String getEasyaccessKey() {
        return easyaccessKey;
    }

    public void setEasyaccessKey(String easyaccessKey) {
        this.easyaccessKey = easyaccessKey;
    }

    public String getEasyaccessSerialNumber() {
        return easyaccessSerialNumber;
    }

    public void setEasyaccessSerialNumber(String easyaccessSerialNumber) {
        this.easyaccessSerialNumber = easyaccessSerialNumber;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getOsProductKey() {
        return osProductKey;
    }

    public void setOsProductKey(String osProductKey) {
        this.osProductKey = osProductKey;
    }

    public Integer getSupportId() {
        return supportId;
    }

    public void setSupportId(Integer supportId) {
        this.supportId = supportId;
    }

    public String getSupportName() {
        return supportName;
    }

    public void setSupportName(String supportName) {
        this.supportName = supportName;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(String contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public String getDataUpdatedOn() {
        return dataUpdatedOn;
    }

    public void setDataUpdatedOn(String dataUpdatedOn) {
        this.dataUpdatedOn = dataUpdatedOn;
    }

    public String getInstrumentTypeId() {
        return instrumentTypeId;
    }

    public void setInstrumentTypeId(String instrumentTypeId) {
        this.instrumentTypeId = instrumentTypeId;
    }

    public String getMcpSerialNumber() {
        return mcpSerialNumber;
    }

    public void setMcpSerialNumber(String mcpSerialNumber) {
        this.mcpSerialNumber = mcpSerialNumber;
    }

    public String getBarcodeReaderSerialNumber() {
        return barcodeReaderSerialNumber;
    }

    public void setBarcodeReaderSerialNumber(String barcodeReaderSerialNumber) {
        this.barcodeReaderSerialNumber = barcodeReaderSerialNumber;
    }

    public String getXt1800iXt2000iPim() {
        return xt1800iXt2000iPim;
    }

    public void setXt1800iXt2000iPim(String xt1800iXt2000iPim) {
        this.xt1800iXt2000iPim = xt1800iXt2000iPim;
    }

    public String getCpuSerialNumber() {
        return cpuSerialNumber;
    }

    public void setCpuSerialNumber(String cpuSerialNumber) {
        this.cpuSerialNumber = cpuSerialNumber;
    }

    public String getMonitorSerialNumber() {
        return monitorSerialNumber;
    }

    public void setMonitorSerialNumber(String monitorSerialNumber) {
        this.monitorSerialNumber = monitorSerialNumber;
    }

    public String getKeyboardSerialNumber() {
        return keyboardSerialNumber;
    }

    public void setKeyboardSerialNumber(String keyboardSerialNumber) {
        this.keyboardSerialNumber = keyboardSerialNumber;
    }

    public String getMouseSerialNumber() {
        return mouseSerialNumber;
    }

    public void setMouseSerialNumber(String mouseSerialNumber) {
        this.mouseSerialNumber = mouseSerialNumber;
    }

    public String getPrinterModel() {
        return printerModel;
    }

    public void setPrinterModel(String printerModel) {
        this.printerModel = printerModel;
    }

    public String getPrinterSerialNumber() {
        return printerSerialNumber;
    }

    public void setPrinterSerialNumber(String printerSerialNumber) {
        this.printerSerialNumber = printerSerialNumber;
    }

    public String getUpsModel() {
        return upsModel;
    }

    public void setUpsModel(String upsModel) {
        this.upsModel = upsModel;
    }

    public String getUpsSerialNumber() {
        return upsSerialNumber;
    }

    public void setUpsSerialNumber(String upsSerialNumber) {
        this.upsSerialNumber = upsSerialNumber;
    }

    public String getInstrumentSerialNumber() {
        return instrumentSerialNumber;
    }

    public void setInstrumentSerialNumber(String instrumentSerialNumber) {
        this.instrumentSerialNumber = instrumentSerialNumber;
    }

    public String getPneumaticUnitSerialNumber() {
        return pneumaticUnitSerialNumber;
    }

    public void setPneumaticUnitSerialNumber(String pneumaticUnitSerialNumber) {
        this.pneumaticUnitSerialNumber = pneumaticUnitSerialNumber;
    }

    public String getSampleLoaderSerialNumber() {
        return sampleLoaderSerialNumber;
    }

    public void setSampleLoaderSerialNumber(String sampleLoaderSerialNumber) {
        this.sampleLoaderSerialNumber = sampleLoaderSerialNumber;
    }
}
