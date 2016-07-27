<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:vch="http://tourist-voucher.com" xmlns:htl="http://tourist-voucher.com/hotel">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Tourist Vouchers</h2>
                <table border="1">
                    <tr align="center">
                        <b>
                            <td rowspan="2">ID</td>
                            <td rowspan="2">Type</td>
                            <td rowspan="2">Country</td>
                            <td rowspan="2">Transport</td>
                            <td colspan="5">Hotel</td>
                            <td rowspan="2">Cost</td>
                        </b>
                    </tr>
                    <tr align="center">
                        <td>Stars</td>
                        <td>Meals</td>
                        <td>Number of persons</td>
                        <td>Air Condition</td>
                        <td>TV</td>

                    </tr>
                     <xsl:apply-templates/>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="vch:touristVoucher">
        <tr align="center">
            <td>
                <xsl:value-of select="@id"/>
            </td>
            <td>
                <xsl:value-of select="vch:type"/>
            </td>
            <td>
                <xsl:value-of select="vch:country"/>
            </td>
            <td>
                <xsl:value-of select="vch:transport"/>
            </td>
            <xsl:apply-templates select="htl:hotel"/>
            <td>
                <xsl:value-of select="vch:cost"/>
                <br/>
                (transfer is
                <xsl:if test="vch:cost/@transferIncluded = 'false'">not</xsl:if>
                included)
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="htl:hotel">
        <td>
            <xsl:value-of select="htl:stars"/>
        </td>
        <td>
            <xsl:value-of select="htl:meals"/>
        </td>
        <td>
            <xsl:value-of select="htl:numberOfPersons"/>
        </td>
        <td>
            <xsl:choose>
                <xsl:when test="htl:airCondition/@available = 'true'">Available</xsl:when>
                <xsl:otherwise>Not available</xsl:otherwise>
            </xsl:choose>
        </td>
        <td>
            <xsl:choose>
                <xsl:when test="htl:tv/@available = 'true'">Available</xsl:when>
                <xsl:otherwise>Not available</xsl:otherwise>
            </xsl:choose>
        </td>
    </xsl:template>

</xsl:stylesheet>