/**
Open Bank Project - API
Copyright (C) 2011-2016, TESOBE Ltd

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Email: contact@tesobe.com
TESOBE Ltd
Osloerstrasse 16/17
Berlin 13359, Germany

  This product includes software developed at
  TESOBE (http://www.tesobe.com/)
  by
  Simon Redfern : simon AT tesobe DOT com
  Stefan Bethge : stefan AT tesobe DOT com
  Everett Sochowski : everett AT tesobe DOT com
  Ayoub Benali: ayoub AT tesobe DOT com

 */
package code.api.v2_1_0

import java.util.Date

import code.api.util.ApiRole
import code.api.v1_2_1.AmountOfMoneyJSON
import code.api.v1_4_0.JSONFactory1_4_0.{DriveUpJson,LicenseJson,ChallengeJSON, CustomerFaceImageJson, MetaJson, TransactionRequestAccountJSON,AddressJson,LocationJson,LobbyJson}
import code.api.v2_0_0.TransactionRequestChargeJSON
import code.branches.Branches.BranchId
import code.common.{License, Meta}
import code.customer.Customer
import code.metadata.counterparties.CounterpartyTrait
import code.model._
import code.transactionrequests.TransactionRequests._
import code.model.{AmountOfMoney, Consumer, Iban}
import code.metadata.counterparties.CounterpartyTrait
import net.liftweb.common.{Box, Full}
import net.liftweb.json.JValue
import code.products.Products.Product

case class TransactionRequestTypeJSON(transaction_request_type: String)
case class TransactionRequestTypesJSON(transaction_request_types: List[TransactionRequestTypeJSON])

case class AvailableRoleJSON(role: String, requires_bank_id: Boolean)
case class AvailableRolesJSON(roles: List[AvailableRoleJSON])

trait TransactionRequestDetailsJSON {
  val value : AmountOfMoneyJSON
}
case class CounterpartyIdJson (val counterpartyId : String)
case class IbanJson (val iban : String)

case class TransactionRequestDetailsSandBoxTanJSON(
                                        to: TransactionRequestAccountJSON,
                                        value : AmountOfMoneyJSON,
                                        description : String
                                      ) extends TransactionRequestDetailsJSON

case class TransactionRequestDetailsSandBoxTanResponseJSON(
                                                            toAccount: TransactionRequestAccountJSON,
                                                            value: AmountOfMoneyJSON,
                                                            description: String
                                                          ) extends TransactionRequestDetailsJSON

case class TransactionRequestDetailsCounterpartyJSON(
                                                    to: CounterpartyIdJson,
                                                    value : AmountOfMoneyJSON,
                                                    description : String
                                                  ) extends TransactionRequestDetailsJSON

case class TransactionRequestDetailsCounterpartyResponseJSON(
                                                            counterpartyId: String,
                                                            toAccount: TransactionRequestAccountJSON,
                                                            value: AmountOfMoneyJSON,
                                                            description: String
                                                          ) extends TransactionRequestDetailsJSON

case class TransactionRequestDetailsSEPAJSON(
                                              value: AmountOfMoneyJSON,
                                              to: IbanJson,
                                              description: String
                                            ) extends TransactionRequestDetailsJSON

case class TransactionRequestDetailsSEPAResponseJSON(
                                                      iban: String,
                                                      toAccount: TransactionRequestAccountJSON,
                                                      value: AmountOfMoneyJSON,
                                                      description: String
                                            ) extends TransactionRequestDetailsJSON

case class TransactionRequestDetailsFreeFormJSON(
                                                  value : AmountOfMoneyJSON
                                            ) extends TransactionRequestDetailsJSON

case class TransactionRequestDetailsFreeFormResponseJSON(
                                                          toAccount: TransactionRequestAccountJSON,
                                                          value: AmountOfMoneyJSON,
                                                          description: String
                                                       ) extends TransactionRequestDetailsJSON



case class TransactionRequestWithChargeJSON210(
                                             id: String,
                                             `type`: String,
                                             from: TransactionRequestAccountJSON,
                                             details: JValue,
                                             transaction_ids: List[String],
                                             status: String,
                                             start_date: Date,
                                             end_date: Date,
                                             challenge: ChallengeJSON,
                                             charge : TransactionRequestChargeJSON
                                           )

case class TransactionRequestWithChargeJSONs210(
                                              transaction_requests_with_charges : List[TransactionRequestWithChargeJSON210]
                                            )
case class PutEnabledJSON(enabled: Boolean)
case class ConsumerJSON(id: Long, name: String, appType: String, description: String, developerEmail: String, enabled: Boolean, created: Date)
case class ConsumerJSONs(list: List[ConsumerJSON])

case class PostCounterpartyJSON(name: String,
                                other_bank_id: String,
                                other_account_id: String,
                                other_account_provider: String,
                                account_routing_scheme: String,
                                account_routing_address: String,
                                bank_routing_scheme: String,
                                bank_routing_address: String,
                                is_beneficiary: Boolean
                               )


case class CounterpartiesJSON(
                              counterpaties: List[CounterpartyJSON]
                             )

case class CounterpartyJSON(
                             counterparty_id: String,
                             display: CounterpartyNameJSON,
                             created_by_user_id: String,
                             this_account: UsedByAccountJSON,
                             account_routing: AccountRoutingJSON,
                             bank_routing: BankRoutingJSON,
                             metadata: CounterpartyMetadataJSON
                           )

case class CounterpartyMetadataJSON(
                                     public_alias: String,
                                     private_alias: String,
                                     more_info: String,
                                     URL: String,
                                     image_URL: String,
                                     open_corporates_URL: String,
                                     corporate_location: LocationJSON,
                                     physical_location: LocationJSON
                                   )

case class AccountRoutingJSON(
                               scheme: String,
                               address: String
                             )

case class BankRoutingJSON(
                               scheme: String,
                               address: String
                             )


case class UsedByAccountJSON(
                              bank_id: String,
                              account_id: String
                            )


case class CounterpartyNameJSON(
                              name: String,
                              is_alias: Boolean
                            )


case class LocationJSON(
                         latitude: Double,
                         longitude: Double,
                         date: Date,
                         user: UserJSON
                       )

case class UserJSON(
                     id: String,
                     provider: String,
                     username: String
                   )


case class PostCustomerJson(
                             user_id: String,
                             customer_number : String,
                             legal_name : String,
                             mobile_phone_number : String,
                             email : String,
                             face_image : CustomerFaceImageJson,
                             date_of_birth: Date,
                             relationship_status: String,
                             dependants: Int,
                             dob_of_dependants: List[Date],
                             credit_rating: CustomerCreditRatingJSON,
                             credit_limit: AmountOfMoneyJSON,
                             highest_education_attained: String,
                             employment_status: String,
                             kyc_status: Boolean,
                             last_ok_date: Date)

case class CustomerJson(customer_id: String,
                        customer_number : String,
                        legal_name : String,
                        mobile_phone_number : String,
                        email : String,
                        face_image : CustomerFaceImageJson,
                        date_of_birth: Date,
                        relationship_status: String,
                        dependants: Int,
                        dob_of_dependants: List[Date],
                        credit_rating: Option[CustomerCreditRatingJSON],
                        credit_limit: Option[AmountOfMoneyJSON],
                        highest_education_attained: String,
                        employment_status: String,
                        kyc_status: Boolean,
                        last_ok_date: Date)
case class CustomerJSONs(customers: List[CustomerJson])

case class CustomerCreditRatingJSON(rating: String, source: String)

//V210 added details and description feilds
case class ProductJson(code : String,
                       name : String,
                       category: String,
                       family : String,
                       super_family : String,
                       more_info_url: String,
                       details: String,
                       description: String,
                       meta : MetaJson)
case class ProductsJson (products : List[ProductJson])

//V210 add bank_id feild and delete id
case class BranchJsonPut(
                       bank_id: String,
                       name: String,
                       address: AddressJson,
                       location: LocationJson,
                       meta: MetaJson,
                       lobby: LobbyJson,
                       driveUp: DriveUpJson)

case class BranchJsonPost(
                           id: String,
                           bank_id: String,
                           name: String,
                           address: AddressJson,
                           location: LocationJson,
                           meta: MetaJson,
                           lobby: LobbyJson,
                           driveUp: DriveUpJson)

object JSONFactory210{
  def createTransactionRequestTypeJSON(transactionRequestType : String ) : TransactionRequestTypeJSON = {
    new TransactionRequestTypeJSON(
      transactionRequestType
    )
  }

  def createTransactionRequestTypeJSON(transactionRequestTypes : List[String]) : TransactionRequestTypesJSON = {
    TransactionRequestTypesJSON(transactionRequestTypes.map(createTransactionRequestTypeJSON))
  }


  def createAvailableRoleJSON(role : String ) : AvailableRoleJSON = {
    new AvailableRoleJSON(
      role = role,
      requires_bank_id = ApiRole.valueOf(role).requiresBankId
    )
  }

  def createAvailableRolesJSON(roles : List[String]) : AvailableRolesJSON = {
    AvailableRolesJSON(roles.map(createAvailableRoleJSON))
  }

  //transaction requests

  // TODO Add Error handling and return Error message to the caller here or elsewhere?
  // e.g. if amount is not a number, return "OBP-XXXX Not a Number"
  // e.g. if currency is not a 3 letter ISO code, return "OBP-XXXX Not an ISO currency"


  def getTransactionRequestDetailsSandBoxTanFromJson(details: TransactionRequestDetailsSandBoxTanJSON) : TransactionRequestDetailsSandBoxTan = {
    val toAcc = TransactionRequestAccount (
      bank_id = details.to.bank_id,
      account_id = details.to.account_id
    )
    val amount = AmountOfMoney (
      currency = details.value.currency,
      amount = details.value.amount
    )

    TransactionRequestDetailsSandBoxTan (
      to = toAcc,
      value = amount,
      description = details.description
    )
  }

  def getTransactionRequestDetailsCounterpartyFromJson(details: TransactionRequestDetailsCounterpartyJSON) : TransactionRequestDetailsCounterparty = {
    val toCounterpartyId = CounterpartyId (details.to.counterpartyId)
    val amount = AmountOfMoney (
      currency = details.value.currency,
      amount = details.value.amount
    )

    TransactionRequestDetailsCounterparty (
      to = toCounterpartyId,
      value = amount,
      description = details.description
    )
  }

  def getTransactionRequestDetailsCounterpartyResponseFromJson(details: TransactionRequestDetailsCounterpartyResponseJSON) : TransactionRequestDetailsCounterpartyResponse = {
    val toAcc = TransactionRequestAccount (
      bank_id = details.toAccount.bank_id,
      account_id = details.toAccount.account_id
    )
    val toCounterpartyId = CounterpartyId (
      value = details.counterpartyId
    )
    val amount = AmountOfMoney (
      currency = details.value.currency,
      amount = details.value.amount
    )
    TransactionRequestDetailsCounterpartyResponse (
      toCounterpartyId = toCounterpartyId,
      to=toAcc,
      value = amount,
      description = details.description
    )
  }
  def getTransactionRequestDetailsSEPAFromJson(details: TransactionRequestDetailsSEPAJSON) : TransactionRequestDetailsSEPA = {
    val toAccIban = IbanJson (
      iban = details.to.iban
    )
    val amount = AmountOfMoney (
      currency = details.value.currency,
      amount = details.value.amount
    )

    TransactionRequestDetailsSEPA (
      iban = toAccIban.iban,
      value = amount,
      description = details.description
    )
  }

  def getTransactionRequestDetailsSEPAResponseJSONFromJson(details: TransactionRequestDetailsSEPAResponseJSON) : TransactionRequestDetailsSEPAResponse = {
    val toAcc = TransactionRequestAccount (
      bank_id = details.toAccount.bank_id,
      account_id = details.toAccount.account_id
    )
    val toAccIban = Iban (
      iban = details.iban
    )
    val amount = AmountOfMoney (
      currency = details.value.currency,
      amount = details.value.amount
    )
    TransactionRequestDetailsSEPAResponse (
      iban = toAccIban.iban,
      to=toAcc,
      value = amount,
      description = details.description
    )
  }

  def getTransactionRequestDetailsFreeFormFromJson(details: TransactionRequestDetailsFreeFormJSON) : TransactionRequestDetailsFreeForm = {
    val amount = AmountOfMoney (
      currency = details.value.currency,
      amount = details.value.amount
    )

    TransactionRequestDetailsFreeForm (
      value = amount
    )
  }

  def getTransactionRequestDetailsFreeFormResponseJson(details: TransactionRequestDetailsFreeFormResponseJSON) : TransactionRequestDetailsFreeFormResponse = {
    val toAcc = TransactionRequestAccount (
      bank_id = details.toAccount.bank_id,
      account_id = details.toAccount.account_id
    )
    val amount = AmountOfMoney (
      currency = details.value.currency,
      amount = details.value.amount
    )
    TransactionRequestDetailsFreeFormResponse (
      to=toAcc,
      value = amount,
      description = details.description
    )
  }


  /** Creates v2.1.0 representation of a TransactionType
    *
    * @param tr An internal TransactionRequest instance
    * @return a v2.1.0 representation of a TransactionRequest
    */

  def createTransactionRequestWithChargeJSON(tr : TransactionRequest) : TransactionRequestWithChargeJSON210 = {
    new TransactionRequestWithChargeJSON210(
      id = tr.id.value,
      `type` = tr.`type`,
      from = TransactionRequestAccountJSON (
        bank_id = tr.from.bank_id,
        account_id = tr.from.account_id
      ),
      details = tr.details,
      transaction_ids = tr.transaction_ids::Nil,
      status = tr.status,
      start_date = tr.start_date,
      end_date = tr.end_date,
      // Some (mapped) data might not have the challenge. TODO Make this nicer
      challenge = {
        try {ChallengeJSON (id = tr.challenge.id, allowed_attempts = tr.challenge.allowed_attempts, challenge_type = tr.challenge.challenge_type)}
        // catch { case _ : Throwable => ChallengeJSON (id = "", allowed_attempts = 0, challenge_type = "")}
        catch { case _ : Throwable => null}
      },
      charge = TransactionRequestChargeJSON (summary = tr.charge.summary,
        value = AmountOfMoneyJSON(currency = tr.charge.value.currency,
          amount = tr.charge.value.amount)
      )
    )
  }

  def createTransactionRequestJSONs(trs : List[TransactionRequest]) : TransactionRequestWithChargeJSONs210 = {
    TransactionRequestWithChargeJSONs210(trs.map(createTransactionRequestWithChargeJSON))
  }

  def createConsumerJSON(c: Consumer): ConsumerJSON = {
    ConsumerJSON(id=c.id,
      name=c.name,
      appType=c.appType.toString(),
      description=c.description,
      developerEmail=c.developerEmail,
      enabled=c.isActive,
      created=c.createdAt
    )
  }
  def createConsumerJSONs(l : List[Consumer]): ConsumerJSONs = {
    ConsumerJSONs(l.map(createConsumerJSON))
  }

  def createCounterpartJSON(moderated: ModeratedOtherBankAccount, metadata : CounterpartyMetadata, couterparty: CounterpartyTrait) : CounterpartyJSON = {
    new CounterpartyJSON(
      counterparty_id = metadata.metadataId,
      display = CounterpartyNameJSON(moderated.label.display, moderated.isAlias),
      created_by_user_id = couterparty.createdByUserId,
      this_account = UsedByAccountJSON(couterparty.thisBankId, couterparty.thisAccountId),
      account_routing = AccountRoutingJSON(couterparty.accountRoutingScheme, couterparty.accountRoutingAddress),
      bank_routing = BankRoutingJSON(couterparty.bankRoutingScheme, couterparty.bankRoutingAddress),
      metadata = CounterpartyMetadataJSON(public_alias = metadata.getPublicAlias,
        private_alias = metadata.getPrivateAlias,
        more_info = metadata.getMoreInfo,
        URL = metadata.getUrl,
        image_URL = metadata.getImageURL,
        open_corporates_URL = metadata.getOpenCorporatesURL,
        corporate_location = createLocationJSON(metadata.getCorporateLocation),
        physical_location = createLocationJSON(metadata.getPhysicalLocation)
      )
    )
  }

  def createCounterpartyMetaDataJSON(metadata : ModeratedOtherBankAccountMetadata) : CounterpartyMetadataJSON = {
    new CounterpartyMetadataJSON(
      public_alias = stringOptionOrNull(metadata.publicAlias),
      private_alias = stringOptionOrNull(metadata.privateAlias),
      more_info = stringOptionOrNull(metadata.moreInfo),
      URL = stringOptionOrNull(metadata.url),
      image_URL = stringOptionOrNull(metadata.imageURL),
      open_corporates_URL = stringOptionOrNull(metadata.openCorporatesURL),
      corporate_location = metadata.corporateLocation.map(createLocationJSON).getOrElse(null),
      physical_location = metadata.physicalLocation.map(createLocationJSON).getOrElse(null)
    )
  }

  def createLocationJSON(loc : Option[GeoTag]) : LocationJSON = {
    loc match {
      case Some(location) => {
        val user = createUserJSON(location.postedBy)
        //test if the GeoTag is set to its default value
        if(location.latitude == 0.0 & location.longitude == 0.0 & user == null)
          null
        else
          new LocationJSON(
            latitude = location.latitude,
            longitude = location.longitude,
            date = location.datePosted,
            user = user
          )
      }
      case _ => null
    }
  }

  def createUserJSON(user : Box[User]) : UserJSON = {
    user match {
      case Full(u) => createUserJSON(u)
      case _ => null
    }
  }

  def createUserJSON(user : User) : UserJSON = {
    new UserJSON(
      user.idGivenByProvider,
      stringOrNull(user.provider),
      stringOrNull(user.emailAddress) //TODO: shouldn't this be the display name?
    )
  }


  def stringOrNull(text : String) =
    if(text == null || text.isEmpty)
      null
    else
      text

  def stringOptionOrNull(text : Option[String]) =
    text match {
      case Some(t) => stringOrNull(t)
      case _ => null
    }

  def createCustomerJson(cInfo : Customer) : CustomerJson = {

    CustomerJson(
      customer_id = cInfo.customerId,
      customer_number = cInfo.number,
      legal_name = cInfo.legalName,
      mobile_phone_number = cInfo.mobileNumber,
      email = cInfo.email,
      face_image = CustomerFaceImageJson(url = cInfo.faceImage.url,
        date = cInfo.faceImage.date),
      date_of_birth = cInfo.dateOfBirth,
      relationship_status = cInfo.relationshipStatus,
      dependants = cInfo.dependents,
      dob_of_dependants = cInfo.dobOfDependents,
      credit_rating = Option(CustomerCreditRatingJSON(rating = cInfo.creditRating.rating, source = cInfo.creditRating.source)),
      credit_limit = Option(AmountOfMoneyJSON(currency = cInfo.creditLimit.currency, amount = cInfo.creditLimit.amount)),
      highest_education_attained = cInfo.highestEducationAttained,
      employment_status = cInfo.employmentStatus,
      kyc_status = cInfo.kycStatus,
      last_ok_date = cInfo.lastOkDate
    )
  }
  def createCustomersJson(customers : List[Customer]) : CustomerJSONs = {
    CustomerJSONs(customers.map(createCustomerJson))
  }

  // V210 Products
  def createProductJson(product: Product) : ProductJson = {
    ProductJson(product.code.value,
      product.name,
      product.category,
      product.family,
      product.superFamily,
      product.moreInfoUrl,
      product.details,
      product.description,
      createMetaJson(product.meta))
  }

  def createProductsJson(productsList: List[Product]) : ProductsJson = {
    ProductsJson(productsList.map(createProductJson))
  }
  def createMetaJson(meta: Meta) : MetaJson = {
    MetaJson(createLicenseJson(meta.license))
  }
  // Accept a license object and return its json representation
  def createLicenseJson(license : License) : LicenseJson = {
    LicenseJson(license.id, license.name)
  }

  def toBranchJsonPost(branchId: BranchId, branch: BranchJsonPut): Box[BranchJsonPost] = {
    Full(BranchJsonPost(
      branchId.value,
      branch.bank_id,
      branch.name,
      branch.address,
      branch.location,
      branch.meta,
      branch.lobby,
      branch.driveUp))
  }
}