package id.geekgarden.esi.data.model.tikets.staffticket.model.responseinstalation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rakasettya on 11/16/17.
 */
public class BodyInstallation {

  @SerializedName("phone_number")
  @Expose
  private String phoneNumber;
  @SerializedName("address")
  @Expose
  private String address;
  @SerializedName("contact_person")
  @Expose
  private String contactPerson;
  @SerializedName("fax_number")
  @Expose
  private String faxNumber;
  @SerializedName("grounding")
  @Expose
  private Boolean grounding;
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
  @SerializedName("instrument_serial_number")
  @Expose
  private String instrumentSerialNumber;
  @SerializedName("pneumatic_unit_serial_number")
  @Expose
  private String pneumaticUnitSerialNumber;
  @SerializedName("sample_loader_serial_number")
  @Expose
  private String sampleLoaderSerialNumber;
  @SerializedName("mcp_serial_number")
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
  @SerializedName("contract_type_id")
  @Expose
  private Integer contractTypeId;
  @SerializedName("data_updated_on")
  @Expose
  private String dataUpdatedOn;

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
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

  public String getFaxNumber() {
    return faxNumber;
  }

  public void setFaxNumber(String faxNumber) {
    this.faxNumber = faxNumber;
  }

  public Boolean getGrounding() {
    return grounding;
  }

  public void setGrounding(Boolean grounding) {
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

  public Integer getContractTypeId() {
    return contractTypeId;
  }

  public void setContractTypeId(Integer contractTypeId) {
    this.contractTypeId = contractTypeId;
  }

  public String getDataUpdatedOn() {
    return dataUpdatedOn;
  }

  public void setDataUpdatedOn(String dataUpdatedOn) {
    this.dataUpdatedOn = dataUpdatedOn;
  }

}