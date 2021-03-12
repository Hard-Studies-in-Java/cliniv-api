package br.com.nivlabs.gp.report.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Objeto mapeado do XML Jasper
 * 
 * @author viniciosarodrigues
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JasperReportXml implements Serializable {

    private static final long serialVersionUID = 3811672384675396684L;

    @JacksonXmlProperty(localName = "parameter")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<JasperParameterXml> parameters = new ArrayList<>();

    public JasperReportXml() {
        super();
    }

    public JasperReportXml(List<JasperParameterXml> parameters) {
        super();
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "JasperReportXml [parameters=" + parameters + "]";
    }

    public List<JasperParameterXml> getParameters() {
        return parameters;
    }

    public void setParameters(List<JasperParameterXml> parameters) {
        this.parameters = parameters;
    }

    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * @param args
     * @throws JsonProcessingException
     */
    public static void main(String[] args) throws JsonProcessingException {
        XmlMapper mapper = new XmlMapper();
        JasperReportXml reportXml = mapper.readValue(getXml(), JasperReportXml.class);
        reportXml.getParameters().forEach(parameter ->

        System.out.println(
                           "Parâmetro : " + parameter.getName() + " | Tipo: " + parameter.getType()
                                   + "\n     "
                                   + "Descrição do parâmetro: " + parameter.getParameterDescription()
                                   + "\n     "
                                   + "Valor padrão do parâmetro: " + parameter.getDefaultValueExpression()
                                   + "\n\n"));

    }

    public static String getXml() {
        return """
<?xml version="1.0" encoding="UTF-8"?>
                <!-- Created with Jaspersoft Studio version 6.13.0.final using JasperReports Library version 6.13.0-46ada4d1be8f3c5985fd0b6146f3ed44caed6f05  -->
                <jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="Anamnese" pageWidth="595" pageHeight="842" columnWidth="555" leftMargin="20" rightMargin="20" topMargin="20" bottomMargin="20" uuid="68c5ae44-61b1-41d2-8da8-418815b473fe">
                        <property name="com.jaspersoft.studio.data.sql.tables" value=""/>
                        <property name="com.jaspersoft.studio.data.defaultdataadapter" value="DataAdapter.xml"/>
                        <subDataset name="ProdDb" uuid="e728a847-0768-47ee-8483-3cc4246c57fc">
                                <property name="com.jaspersoft.studio.data.sql.tables" value=""/>
                                <property name="com.jaspersoft.studio.data.defaultdataadapter" value="DataAdapter.xml"/>
                                <parameter name="VISIT_ID" class="java.lang.String"/>
                                <queryString language="SQL">
                                        <![CDATA[SELECT
                        -- CABEÇALHO DO PACIENTE
                        PACIENTE.ID AS ID_PACIENTE,
                    CASE
                                WHEN PACIENTE.CODIGO_SUS IS NOT NULL THEN PACIENTE.CODIGO_SUS
                                ELSE 'N/I'
                        END AS CNS,
                    PESSOA.NOME_COMPLETO,
                    CASE
                                WHEN PESSOA.DATA_NASCIMENTO IS NOT NULL THEN PESSOA.DATA_NASCIMENTO
                        ELSE 'N/I'
                        END AS DATA_NASCIMENTO,
                    CASE
                                WHEN PESSOA.RG IS NOT NULL THEN PESSOA.RG
                        ELSE 'N/I'
                        END AS RG,
                    CASE
                                WHEN PESSOA.CPF IS NOT NULL THEN PESSOA.CPF
                        ELSE 'N/I'
                        END AS CPF,
                    CASE
                                WHEN PESSOA.SEXO = 'M' THEN 'MASCULINO'
                        ELSE 'FEMININO'
                        END AS SEXO,
                    CASE
                                WHEN PESSOA.IDENTIDADE_GENERO LIKE 'HETERO' THEN 'HETEROSSEXUAL'
                        ELSE 'TRANSGÊNERO/HOMOSSEXUAL'
                        END AS IDEOLOGIA,
                    CASE
                                WHEN PESSOA.NOME_COMP_MAE IS NOT NULL THEN PESSOA.NOME_COMP_MAE
                        ELSE 'N/I'
                        END AS NOME_COMP_MAE,

                    -- INFORMAÇÕES DO ATENDIMENTO
                    VISITA.ID AS ID_VISITA,
                    VISITA.TIPO_ENTRADA,
                    VISITA.DH_ENTRADA,
                    CASE
                                WHEN VISITA.DH_SAIDA IS NOT NULL THEN VISITA.DH_SAIDA
                        ELSE 'N/I'
                        END DH_SAIDA,

                    -- INFORMAÇÕES DA INSTITUIÇÃO
                    CONCAT(INSTITUTO.LOGRADOURO, ', ', INSTITUTO.NUMERO, CASE WHEN INSTITUTO.COMPLEMENTO IS NULL THEN ' - ' ELSE CONCAT(' - Complemento: ', INSTITUTO.COMPLEMENTO, " - ") END,  INSTITUTO.BAIRRO, " - ", INSTITUTO.CIDADE, " - ", INSTITUTO.ESTADO) AS HOSP_ENDERECO,
                    INSTITUTO.TELEFONE AS HOSP_TELEFONE
                FROM VISITA VISITA
                        INNER JOIN PACIENTE PACIENTE ON PACIENTE.ID = VISITA.ID_PACIENTE
                        INNER JOIN PESSOA_FISICA PESSOA ON PESSOA.ID = PACIENTE.ID_PESSOA
                    INNER JOIN INSTITUTO
                WHERE VISITA.ID = $P{VISIT_ID};]]>
                                </queryString>
                                <field name="ID_PACIENTE" class="java.lang.Long">
                                        <property name="com.jaspersoft.studio.field.label" value="ID_PACIENTE"/>
                                        <property name="com.jaspersoft.studio.field.tree.path" value="PACIENTE"/>
                                </field>
                                <field name="CNS" class="java.lang.String">
                                        <property name="com.jaspersoft.studio.field.label" value="CNS"/>
                                </field>
                                <field name="NOME_COMPLETO" class="java.lang.String">
                                        <property name="com.jaspersoft.studio.field.label" value="NOME_COMPLETO"/>
                                </field>
                                <field name="DATA_NASCIMENTO" class="java.lang.String">
                                        <property name="com.jaspersoft.studio.field.label" value="DATA_NASCIMENTO"/>
                                </field>
                                <field name="RG" class="java.lang.String">
                                        <property name="com.jaspersoft.studio.field.label" value="RG"/>
                                </field>
                                <field name="CPF" class="java.lang.String">
                                        <property name="com.jaspersoft.studio.field.label" value="CPF"/>
                                </field>
                                <field name="SEXO" class="java.lang.String">
                                        <property name="com.jaspersoft.studio.field.label" value="SEXO"/>
                                </field>
                                <field name="IDEOLOGIA" class="java.lang.String">
                                        <property name="com.jaspersoft.studio.field.label" value="IDEOLOGIA"/>
                                </field>
                                <field name="NOME_COMP_MAE" class="java.lang.String">
                                        <property name="com.jaspersoft.studio.field.label" value="NOME_COMP_MAE"/>
                                </field>
                                <field name="ID_VISITA" class="java.lang.Long">
                                        <property name="com.jaspersoft.studio.field.label" value="ID_VISITA"/>
                                        <property name="com.jaspersoft.studio.field.tree.path" value="VISITA"/>
                                </field>
                                <field name="TIPO_ENTRADA" class="java.lang.String">
                                        <property name="com.jaspersoft.studio.field.label" value="TIPO_ENTRADA"/>
                                        <property name="com.jaspersoft.studio.field.tree.path" value="VISITA"/>
                                </field>
                                <field name="DH_ENTRADA" class="java.sql.Timestamp">
                                        <property name="com.jaspersoft.studio.field.label" value="DH_ENTRADA"/>
                                        <property name="com.jaspersoft.studio.field.tree.path" value="VISITA"/>
                                </field>
                                <field name="DH_SAIDA" class="java.lang.String">
                                        <property name="com.jaspersoft.studio.field.label" value="DH_SAIDA"/>
                                </field>
                                <field name="HOSP_ENDERECO" class="java.lang.String">
                                        <property name="com.jaspersoft.studio.field.label" value="HOSP_ENDERECO"/>
                                </field>
                                <field name="HOSP_TELEFONE" class="java.lang.String">
                                        <property name="com.jaspersoft.studio.field.label" value="HOSP_TELEFONE"/>
                                        <property name="com.jaspersoft.studio.field.tree.path" value="INSTITUTO"/>
                                </field>
                        </subDataset>
                        <parameter name="VISIT_ID" class="java.lang.Long" nestedType="java.lang.Long">
                                <parameterDescription><![CDATA[CÓDIGO DO ATENDIMENTO]]></parameterDescription>
                        </parameter>
                        <parameter name="HOSPITAL_LOGO" class="java.lang.String"/>
                        <parameter name="DOC_TITLE" class="java.lang.String"/>
                        <parameter name="READER_NAME" class="java.lang.String"/>
                        <parameter name="TODAY" class="java.util.Date"/>
                        <parameter name="DESCRICAO" class="java.lang.String"/>
                        <queryString language="SQL">
                                <![CDATA[SELECT
                        -- CABEÇALHO DO PACIENTE
                        PACIENTE.ID AS ID_PACIENTE,
                    CASE
                                WHEN PACIENTE.CODIGO_SUS IS NOT NULL THEN PACIENTE.CODIGO_SUS
                                ELSE 'N/I'
                        END AS CNS,
                    PESSOA.NOME_COMPLETO,
                    CASE
                                WHEN PESSOA.DATA_NASCIMENTO IS NOT NULL THEN PESSOA.DATA_NASCIMENTO
                        ELSE 'N/I'
                        END AS DATA_NASCIMENTO,
                    CASE
                                WHEN PESSOA.RG IS NOT NULL THEN PESSOA.RG
                        ELSE 'N/I'
                        END AS RG,
                    CASE
                                WHEN PESSOA.CPF IS NOT NULL THEN PESSOA.CPF
                        ELSE 'N/I'
                        END AS CPF,
                    CASE
                                WHEN PESSOA.SEXO = 'M' THEN 'MASCULINO'
                        ELSE 'FEMININO'
                        END AS SEXO,
                    CASE
                                WHEN PESSOA.IDENTIDADE_GENERO LIKE 'HETERO' THEN 'HETEROSSEXUAL'
                        ELSE 'TRANSGÊNERO/HOMOSSEXUAL'
                        END AS IDEOLOGIA,
                    CASE
                                WHEN PESSOA.NOME_COMP_MAE IS NOT NULL THEN PESSOA.NOME_COMP_MAE
                        ELSE 'N/I'
                        END AS NOME_COMP_MAE,

                    -- INFORMAÇÕES DO ATENDIMENTO
                    VISITA.ID AS ID_VISITA,
                    VISITA.TIPO_ENTRADA,
                    VISITA.DH_ENTRADA,
                    CASE
                                WHEN VISITA.DH_SAIDA IS NOT NULL THEN VISITA.DH_SAIDA
                        ELSE 'N/I'
                        END DH_SAIDA,

                    -- INFORMAÇÕES DA INSTITUIÇÃO
                    CONCAT(INSTITUTO.LOGRADOURO, ', ', INSTITUTO.NUMERO, CASE WHEN INSTITUTO.COMPLEMENTO IS NULL THEN ' - ' ELSE CONCAT(' - Complemento: ', INSTITUTO.COMPLEMENTO, " - ") END,  INSTITUTO.BAIRRO, " - ", INSTITUTO.CIDADE, " - ", INSTITUTO.ESTADO) AS HOSP_ENDERECO,
                    INSTITUTO.TELEFONE AS HOSP_TELEFONE
                FROM VISITA VISITA
                        INNER JOIN PACIENTE PACIENTE ON PACIENTE.ID = VISITA.ID_PACIENTE
                        INNER JOIN PESSOA_FISICA PESSOA ON PESSOA.ID = PACIENTE.ID_PESSOA
                    INNER JOIN INSTITUTO
                WHERE VISITA.ID = $P{VISIT_ID};]]>
                        </queryString>
                        <field name="ID_PACIENTE" class="java.lang.Long">
                                <property name="com.jaspersoft.studio.field.label" value="ID_PACIENTE"/>
                                <property name="com.jaspersoft.studio.field.tree.path" value="PACIENTE"/>
                        </field>
                        <field name="CNS" class="java.lang.String">
                                <property name="com.jaspersoft.studio.field.label" value="CNS"/>
                                <property name="com.jaspersoft.studio.field.tree.path" value="PACIENTE"/>
                        </field>
                        <field name="NOME_COMPLETO" class="java.lang.String">
                                <property name="com.jaspersoft.studio.field.label" value="NOME_COMPLETO"/>
                        </field>
                        <field name="DATA_NASCIMENTO" class="java.lang.String">
                                <property name="com.jaspersoft.studio.field.label" value="DATA_NASCIMENTO"/>
                                <property name="com.jaspersoft.studio.field.tree.path" value="PESSOA_FISICA"/>
                        </field>
                        <field name="RG" class="java.lang.String">
                                <property name="com.jaspersoft.studio.field.label" value="RG"/>
                                <property name="com.jaspersoft.studio.field.tree.path" value="PESSOA_FISICA"/>
                        </field>
                        <field name="CPF" class="java.lang.String">
                                <property name="com.jaspersoft.studio.field.label" value="CPF"/>
                                <property name="com.jaspersoft.studio.field.tree.path" value="PESSOA_FISICA"/>
                        </field>
                        <field name="SEXO" class="java.lang.String">
                                <property name="com.jaspersoft.studio.field.label" value="SEXO"/>
                        </field>
                        <field name="IDEOLOGIA" class="java.lang.String">
                                <property name="com.jaspersoft.studio.field.label" value="IDEOLOGIA"/>
                        </field>
                        <field name="NOME_COMP_MAE" class="java.lang.String">
                                <property name="com.jaspersoft.studio.field.label" value="NOME_COMP_MAE"/>
                                <property name="com.jaspersoft.studio.field.tree.path" value="PESSOA_FISICA"/>
                        </field>
                        <field name="ID_VISITA" class="java.lang.Long">
                                <property name="com.jaspersoft.studio.field.label" value="ID_VISITA"/>
                                <property name="com.jaspersoft.studio.field.tree.path" value="VISITA"/>
                        </field>
                        <field name="TIPO_ENTRADA" class="java.lang.String">
                                <property name="com.jaspersoft.studio.field.label" value="TIPO_ENTRADA"/>
                                <property name="com.jaspersoft.studio.field.tree.path" value="VISITA"/>
                        </field>
                        <field name="DH_ENTRADA" class="java.sql.Timestamp">
                                <property name="com.jaspersoft.studio.field.label" value="DH_ENTRADA"/>
                                <property name="com.jaspersoft.studio.field.tree.path" value="VISITA"/>
                        </field>
                        <field name="DH_SAIDA" class="java.lang.String">
                                <property name="com.jaspersoft.studio.field.label" value="DH_SAIDA"/>
                                <property name="com.jaspersoft.studio.field.tree.path" value="VISITA"/>
                        </field>
                        <field name="HOSP_ENDERECO" class="java.lang.String">
                                <property name="com.jaspersoft.studio.field.label" value="HOSP_ENDERECO"/>
                        </field>
                        <field name="HOSP_TELEFONE" class="java.lang.String">
                                <property name="com.jaspersoft.studio.field.label" value="HOSP_TELEFONE"/>
                                <property name="com.jaspersoft.studio.field.tree.path" value="INSTITUTO"/>
                        </field>
                        <background>
                                <band splitType="Stretch"/>
                        </background>
                        <pageHeader>
                                <band height="235" splitType="Stretch">
                                        <property name="com.jaspersoft.studio.layout" value="com.jaspersoft.studio.editor.layout.FreeLayout"/>
                                        <image hAlign="Center">
                                                <reportElement x="0" y="0" width="179" height="79" uuid="893db2f2-5d90-4191-985f-f2cca18b90ec">
                                                        <property name="com.jaspersoft.studio.unit.width" value="px"/>
                                                        <property name="com.jaspersoft.studio.unit.height" value="px"/>
                                                </reportElement>
                                                <box topPadding="0" leftPadding="0" bottomPadding="0" rightPadding="0">
                                                        <topPen lineWidth="1.0" lineStyle="Solid" lineColor="#000000"/>
                                                        <leftPen lineWidth="1.0" lineStyle="Solid" lineColor="#000000"/>
                                                        <bottomPen lineWidth="1.0" lineStyle="Solid" lineColor="#000000"/>
                                                        <rightPen lineWidth="1.0" lineStyle="Solid" lineColor="#000000"/>
                                                </box>
                                                <imageExpression><![CDATA[new java.io.ByteArrayInputStream(org.apache.commons.codec.binary.Base64.decodeBase64($P{HOSPITAL_LOGO}.getBytes()))]]></imageExpression>
                                        </image>
                                        <rectangle>
                                                <reportElement x="0" y="102" width="555" height="128" uuid="ac6587b4-3039-40c9-bc85-4d8b35dc0909"/>
                                        </rectangle>
                                        <rectangle>
                                                <reportElement x="380" y="0" width="175" height="79" uuid="d5dd515f-6857-4350-815f-6a12a9f509fd"/>
                                        </rectangle>
                                        <rectangle>
                                                <reportElement x="179" y="0" width="201" height="79" uuid="706336b4-6dc1-418d-b386-7cfd9941203f"/>
                                        </rectangle>
                                        <rectangle>
                                                <reportElement x="380" y="0" width="175" height="40" uuid="e655e1de-1d09-44ac-8b6d-0d40175e44a8"/>
                                        </rectangle>
                                        <rectangle>
                                                <reportElement x="0" y="79" width="555" height="23" uuid="f22c7369-5a4f-4e6d-bcde-84c3f6950c9b"/>
                                        </rectangle>
                                        <textField isBlankWhenNull="true">
                                                <reportElement x="0" y="79" width="554" height="22" uuid="2bb8a103-2f53-483b-a78b-837d4c9f4aa9"/>
                                                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                                                        <font size="11"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$P{DOC_TITLE}]]></textFieldExpression>
                                        </textField>
                                        <componentElement>
                                                <reportElement mode="Transparent" x="391" y="45" width="153" height="29" uuid="ee7c42d3-e5e0-4060-a3e3-5b930138367c"/>
                                                <jr:Code39 xmlns:jr="http://jasperreports.sourceforge.net/jasperreports/components" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd" textPosition="none" verticalQuietZone="0.0">
                                                        <jr:codeExpression><![CDATA[String.format("%15d", $F{ID_VISITA})]]></jr:codeExpression>
                                                </jr:Code39>
                                        </componentElement>
                                        <textField>
                                                <reportElement x="190" y="10" width="180" height="36" uuid="b61c24f4-9e97-4a2d-8cea-6970fa1e1632"/>
                                                <textElement textAlignment="Center">
                                                        <font size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$F{HOSP_ENDERECO}]]></textFieldExpression>
                                        </textField>
                                        <textField>
                                                <reportElement x="190" y="49" width="180" height="30" uuid="a140a406-d175-4a43-ae0d-82fa2f79a3ac"/>
                                                <textElement textAlignment="Center">
                                                        <font size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA["Tel: " + $F{HOSP_TELEFONE}]]></textFieldExpression>
                                        </textField>
                                        <textField>
                                                <reportElement x="391" y="20" width="152" height="14" uuid="ec0bb6bc-4716-4cd0-9b14-6ed1252f385f"/>
                                                <textElement textAlignment="Center" verticalAlignment="Middle"/>
                                                <textFieldExpression><![CDATA[$F{ID_VISITA}]]></textFieldExpression>
                                        </textField>
                                        <staticText>
                                                <reportElement x="417" y="0" width="100" height="18" uuid="aa8080c8-efea-415b-ae8e-b05d2f29d2a4"/>
                                                <textElement textAlignment="Center" verticalAlignment="Middle"/>
                                                <text><![CDATA[Atendimento]]></text>
                                        </staticText>
                                        <textField>
                                                <reportElement x="125" y="138" width="174" height="15" isRemoveLineWhenBlank="true" uuid="cfdc6fb1-8103-41c5-b7de-9d51fdf87129"/>
                                                <textElement>
                                                        <font fontName="SansSerif" size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$F{CNS}]]></textFieldExpression>
                                        </textField>
                                        <textField>
                                                <reportElement x="455" y="108" width="94" height="15" isRemoveLineWhenBlank="true" uuid="fa330578-b8f7-4f96-9fb0-55a62d28c29b"/>
                                                <textElement>
                                                        <font fontName="SansSerif" size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$F{ID_VISITA}]]></textFieldExpression>
                                        </textField>
                                        <textField>
                                                <reportElement x="125" y="108" width="174" height="15" isRemoveLineWhenBlank="true" uuid="f5960242-4fc2-4888-9310-0b5466acc809"/>
                                                <textElement>
                                                        <font fontName="SansSerif" size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$F{ID_PACIENTE}]]></textFieldExpression>
                                        </textField>
                                        <textField>
                                                <reportElement x="125" y="153" width="174" height="15" isRemoveLineWhenBlank="true" uuid="c37f0b87-b590-42e8-aa7d-a7dfc3e69124"/>
                                                <textElement>
                                                        <font fontName="SansSerif" size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$F{DATA_NASCIMENTO}]]></textFieldExpression>
                                        </textField>
                                        <staticText>
                                                <reportElement x="315" y="138" width="134" height="15" uuid="d2278333-7a1a-462a-bc64-3b4865f3accb"/>
                                                <textElement textAlignment="Left">
                                                        <font fontName="SansSerif" size="9"/>
                                                </textElement>
                                                <text><![CDATA[Data/hora saída:]]></text>
                                        </staticText>
                                        <staticText>
                                                <reportElement x="315" y="108" width="134" height="15" uuid="40243bd0-80d8-4daf-8f56-c336d082aff0"/>
                                                <textElement textAlignment="Left">
                                                        <font fontName="SansSerif" size="9"/>
                                                </textElement>
                                                <text><![CDATA[Código do atendimento:]]></text>
                                        </staticText>
                                        <textField>
                                                <reportElement x="455" y="123" width="94" height="15" isRemoveLineWhenBlank="true" uuid="39f4939c-696d-412e-9fb7-91d60ce8b758"/>
                                                <textElement>
                                                        <font fontName="SansSerif" size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$F{DH_ENTRADA}]]></textFieldExpression>
                                        </textField>
                                        <textField>
                                                <reportElement x="125" y="123" width="174" height="15" isRemoveLineWhenBlank="true" uuid="591f340d-9069-4b7d-b675-f07565119d64"/>
                                                <textElement>
                                                        <font fontName="SansSerif" size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$F{NOME_COMPLETO}]]></textFieldExpression>
                                        </textField>
                                        <staticText>
                                                <reportElement x="5" y="123" width="114" height="15" uuid="2ec96511-8e05-4c1e-b830-360b0e581be5"/>
                                                <textElement textAlignment="Left">
                                                        <font fontName="SansSerif" size="9"/>
                                                </textElement>
                                                <text><![CDATA[Nome do Paciente:]]></text>
                                        </staticText>
                                        <staticText>
                                                <reportElement x="5" y="108" width="114" height="15" uuid="b7ac8625-3d6b-4ee9-b50d-b84ad8b59de4"/>
                                                <textElement textAlignment="Left">
                                                        <font fontName="SansSerif" size="9"/>
                                                </textElement>
                                                <text><![CDATA[Código do paciente:]]></text>
                                        </staticText>
                                        <staticText>
                                                <reportElement x="5" y="153" width="114" height="15" uuid="05ab2939-a506-4ef0-8c14-3020267c2f11"/>
                                                <textElement textAlignment="Left">
                                                        <font fontName="SansSerif" size="9"/>
                                                </textElement>
                                                <text><![CDATA[Dt de nascimento:]]></text>
                                        </staticText>
                                        <staticText>
                                                <reportElement x="5" y="168" width="114" height="15" uuid="163ed959-5cef-4e88-87da-30feb7c3f753"/>
                                                <textElement textAlignment="Left">
                                                        <font fontName="SansSerif" size="9"/>
                                                </textElement>
                                                <text><![CDATA[CPF:]]></text>
                                        </staticText>
                                        <staticText>
                                                <reportElement x="315" y="123" width="134" height="15" uuid="7dda9fe5-ed2b-4666-82d9-2541cd1b6359"/>
                                                <textElement textAlignment="Left">
                                                        <font fontName="SansSerif" size="9"/>
                                                </textElement>
                                                <text><![CDATA[Data/hora entrada:]]></text>
                                        </staticText>
                                        <textField>
                                                <reportElement x="125" y="168" width="174" height="15" isRemoveLineWhenBlank="true" uuid="a5b21ce4-65d3-41da-8f9d-a1c3fcdc8304"/>
                                                <textElement>
                                                        <font fontName="SansSerif" size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$F{CPF}]]></textFieldExpression>
                                        </textField>
                                        <staticText>
                                                <reportElement x="5" y="138" width="114" height="15" uuid="0c0b8f05-732f-4404-9517-f890e5c9f2fb"/>
                                                <textElement textAlignment="Left">
                                                        <font fontName="SansSerif" size="9"/>
                                                </textElement>
                                                <text><![CDATA[CNS:]]></text>
                                        </staticText>
                                        <textField>
                                                <reportElement x="125" y="183" width="174" height="15" isRemoveLineWhenBlank="true" uuid="fab09465-9063-4174-bc8a-43d0421d60c4"/>
                                                <textElement>
                                                        <font fontName="SansSerif" size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$F{NOME_COMP_MAE}]]></textFieldExpression>
                                        </textField>
                                        <staticText>
                                                <reportElement x="5" y="183" width="114" height="15" uuid="bd2a0d33-caac-4f9d-a78f-a923a3224c0e"/>
                                                <textElement textAlignment="Left">
                                                        <font fontName="SansSerif" size="9"/>
                                                </textElement>
                                                <text><![CDATA[Nome da mãe:]]></text>
                                        </staticText>
                                        <textField>
                                                <reportElement x="455" y="138" width="94" height="15" isRemoveLineWhenBlank="true" uuid="37604a3a-7880-4ce1-ad8c-004003b026f9"/>
                                                <textElement>
                                                        <font fontName="SansSerif" size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$F{DH_SAIDA}]]></textFieldExpression>
                                        </textField>
                                </band>
                        </pageHeader>
                        <detail>
                                <band height="250" splitType="Stretch">
                                        <rectangle>
                                                <reportElement x="0" y="-5" width="555" height="255" uuid="cd6e81f8-068d-424a-bdf6-4b083798459d"/>
                                        </rectangle>
                                        <textField>
                                                <reportElement x="7" y="10" width="542" height="240" uuid="d9f6a75a-536d-46bc-a57f-0cee5e635d24"/>
                                                <textElement>
                                                        <font size="10"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$P{DESCRICAO}]]></textFieldExpression>
                                        </textField>
                                </band>
                        </detail>
                        <pageFooter>
                                <band height="83" splitType="Stretch">
                                        <property name="com.jaspersoft.studio.layout" value="com.jaspersoft.studio.editor.layout.FreeLayout"/>
                                        <textField>
                                                <reportElement x="265" y="60" width="290" height="19" forecolor="#000000" uuid="d53bb85a-f1fe-4df4-bf03-f9c485f1f13d"/>
                                                <textElement textAlignment="Right" verticalAlignment="Bottom"/>
                                                <textFieldExpression><![CDATA["Página " + $V{PAGE_NUMBER}]]></textFieldExpression>
                                        </textField>
                                        <staticText>
                                                <reportElement x="7" y="36" width="52" height="12" uuid="8d0ac5c6-b265-4ced-aa8f-f7f08b95a3f7">
                                                        <property name="com.jaspersoft.studio.unit.height" value="px"/>
                                                </reportElement>
                                                <textElement textAlignment="Left" verticalAlignment="Middle">
                                                        <font size="8"/>
                                                </textElement>
                                                <text><![CDATA[Profissional:]]></text>
                                        </staticText>
                                        <staticText>
                                                <reportElement x="7" y="48" width="72" height="12" uuid="937ebee9-0d75-47ec-be8a-b3403144225a">
                                                        <property name="com.jaspersoft.studio.unit.height" value="px"/>
                                                </reportElement>
                                                <textElement textAlignment="Left" verticalAlignment="Middle">
                                                        <font size="8"/>
                                                </textElement>
                                                <text><![CDATA[Data da geração:]]></text>
                                        </staticText>
                                        <textField>
                                                <reportElement x="80" y="48" width="100" height="12" uuid="12771c67-bae7-4783-844e-08419caa8122"/>
                                                <textElement verticalAlignment="Middle">
                                                        <font size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$P{TODAY}]]></textFieldExpression>
                                        </textField>
                                        <textField>
                                                <reportElement x="80" y="36" width="160" height="12" uuid="3f5819ea-eb3a-466e-abd0-fe66c2c4eee0"/>
                                                <textElement verticalAlignment="Middle">
                                                        <font size="8"/>
                                                </textElement>
                                                <textFieldExpression><![CDATA[$P{READER_NAME}]]></textFieldExpression>
                                        </textField>
                                </band>
                        </pageFooter>
                </jasperReport>

                                                """;
    }

}
