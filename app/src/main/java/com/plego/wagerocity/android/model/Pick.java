package com.plego.wagerocity.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Pick {

    @SerializedName("bet_id")
    @Expose
    private String betId;
    @SerializedName("usr_id")
    @Expose
    private String usrId;
    @SerializedName("odd_id")
    @Expose
    private String oddId;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @Expose
    private String pos;
    @SerializedName("odd_type")
    @Expose
    private String oddType;
    @SerializedName("match_det")
    @Expose
    private String matchDet;
    @SerializedName("odds_val")
    @Expose
    private String oddsVal;
    @Expose
    private String stake;
    @SerializedName("bet_type")
    @Expose
    private String betType;
    @SerializedName("bet_parent")
    @Expose
    private String betParent;
    @SerializedName("bet_created")
    @Expose
    private String betCreated;
    @SerializedName("bet_result")
    @Expose
    private String betResult;
    @SerializedName("bet_processed")
    @Expose
    private String betProcessed;
    @SerializedName("pool_id")
    @Expose
    private String poolId;
    @SerializedName("match_id")
    @Expose
    private String matchId;
    @SerializedName("teaser_point")
    @Expose
    private String teaserPoint;
    @SerializedName("bet_ot")
    @Expose
    private String betOt;
    @SerializedName("bet_dis_odds")
    @Expose
    private String betDisOdds;
    @SerializedName("league_name")
    @Expose
    private String leagueName;
    @SerializedName("os_bet")
    @Expose
    private String osBet;
    @SerializedName("bet_odd_val")
    @Expose
    private String betOddVal;
    @SerializedName("is_pool_bet")
    @Expose
    private String isPoolBet;
    @Expose
    private String id;
    @SerializedName("subleague_id")
    @Expose
    private String subleagueId;
    @SerializedName("scheduled_time")
    @Expose
    private String scheduledTime;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("cst_scheduled_time")
    @Expose
    private String cstScheduledTime;
    @SerializedName("cst_start_time")
    @Expose
    private String cstStartTime;
    @SerializedName("team_A_number")
    @Expose
    private String teamANumber;
    @SerializedName("team_A_name")
    @Expose
    private String teamAName;
    @SerializedName("team_A_nickname")
    @Expose
    private String teamANickname;
    @SerializedName("team_A_abbr")
    @Expose
    private String teamAAbbr;
    @SerializedName("team_A_score")
    @Expose
    private String teamAScore;
    @SerializedName("team_A_period")
    @Expose
    private String teamAPeriod;
    @SerializedName("team_B_number")
    @Expose
    private String teamBNumber;
    @SerializedName("team_B_name")
    @Expose
    private String teamBName;
    @SerializedName("team_B_nickname")
    @Expose
    private String teamBNickname;
    @SerializedName("team_B_abbr")
    @Expose
    private String teamBAbbr;
    @SerializedName("team_B_score")
    @Expose
    private String teamBScore;
    @SerializedName("team_B_period")
    @Expose
    private String teamBPeriod;
    @SerializedName("mat_period")
    @Expose
    private String matPeriod;
    @SerializedName("mat_timer")
    @Expose
    private String matTimer;
    @SerializedName("mat_status")
    @Expose
    private String matStatus;
    @SerializedName("ref_id")
    @Expose
    private String refId;
    @SerializedName("mat_processed")
    @Expose
    private String matProcessed;
    @SerializedName("mat_created")
    @Expose
    private String matCreated;
    @Expose
    private ArrayList<Odd> odds = new ArrayList<Odd>();
    @Expose
    private User User;
    @Expose
    private String result;
    @SerializedName("team_A_logo")
    @Expose
    private String teamALogo;
    @SerializedName("team_B_logo")
    @Expose
    private String teamBLogo;

    private Odd odd;

    public Odd getOdd() {
        return this.odds.get(0);
    }

//    public void setOdd(Odd odd) {
//        this.odd = odd;
//    }


    /**
     *
     * @return
     * The betId
     */
    public String getBetId() {
        return betId;
    }

    /**
     *
     * @param betId
     * The bet_id
     */
    public void setBetId(String betId) {
        this.betId = betId;
    }

    /**
     *
     * @return
     * The usrId
     */
    public String getUsrId() {
        return usrId;
    }

    /**
     *
     * @param usrId
     * The usr_id
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     *
     * @return
     * The oddId
     */
    public String getOddId() {
        return oddId;
    }

    /**
     *
     * @param oddId
     * The odd_id
     */
    public void setOddId(String oddId) {
        this.oddId = oddId;
    }

    /**
     *
     * @return
     * The teamName
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     *
     * @param teamName
     * The team_name
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     *
     * @return
     * The pos
     */
    public String getPos() {
        return pos;
    }

    /**
     *
     * @param pos
     * The pos
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     *
     * @return
     * The oddType
     */
    public String getOddType() {
        return oddType;
    }

    /**
     *
     * @param oddType
     * The odd_type
     */
    public void setOddType(String oddType) {
        this.oddType = oddType;
    }

    /**
     *
     * @return
     * The matchDet
     */
    public String getMatchDet() {
        return matchDet;
    }

    /**
     *
     * @param matchDet
     * The match_det
     */
    public void setMatchDet(String matchDet) {
        this.matchDet = matchDet;
    }

    /**
     *
     * @return
     * The oddsVal
     */
    public String getOddsVal() {
        return oddsVal;
    }

    /**
     *
     * @param oddsVal
     * The odds_val
     */
    public void setOddsVal(String oddsVal) {
        this.oddsVal = oddsVal;
    }

    /**
     *
     * @return
     * The stake
     */
    public String getStake() {
        return stake;
    }

    /**
     *
     * @param stake
     * The stake
     */
    public void setStake(String stake) {
        this.stake = stake;
    }

    /**
     *
     * @return
     * The betType
     */
    public String getBetType() {
        return betType;
    }

    /**
     *
     * @param betType
     * The bet_type
     */
    public void setBetType(String betType) {
        this.betType = betType;
    }

    /**
     *
     * @return
     * The betParent
     */
    public String getBetParent() {
        return betParent;
    }

    /**
     *
     * @param betParent
     * The bet_parent
     */
    public void setBetParent(String betParent) {
        this.betParent = betParent;
    }

    /**
     *
     * @return
     * The betCreated
     */
    public String getBetCreated() {
        return betCreated;
    }

    /**
     *
     * @param betCreated
     * The bet_created
     */
    public void setBetCreated(String betCreated) {
        this.betCreated = betCreated;
    }

    /**
     *
     * @return
     * The betResult
     */
    public String getBetResult() {
        return betResult;
    }

    /**
     *
     * @param betResult
     * The bet_result
     */
    public void setBetResult(String betResult) {
        this.betResult = betResult;
    }

    /**
     *
     * @return
     * The betProcessed
     */
    public String getBetProcessed() {
        return betProcessed;
    }

    /**
     *
     * @param betProcessed
     * The bet_processed
     */
    public void setBetProcessed(String betProcessed) {
        this.betProcessed = betProcessed;
    }

    /**
     *
     * @return
     * The poolId
     */
    public String getPoolId() {
        return poolId;
    }

    /**
     *
     * @param poolId
     * The pool_id
     */
    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    /**
     *
     * @return
     * The matchId
     */
    public String getMatchId() {
        return matchId;
    }

    /**
     *
     * @param matchId
     * The match_id
     */
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    /**
     *
     * @return
     * The teaserPoint
     */
    public String getTeaserPoint() {
        return teaserPoint;
    }

    /**
     *
     * @param teaserPoint
     * The teaser_point
     */
    public void setTeaserPoint(String teaserPoint) {
        this.teaserPoint = teaserPoint;
    }

    /**
     *
     * @return
     * The betOt
     */
    public String getBetOt() {
        return betOt;
    }

    /**
     *
     * @param betOt
     * The bet_ot
     */
    public void setBetOt(String betOt) {
        this.betOt = betOt;
    }

    /**
     *
     * @return
     * The betDisOdds
     */
    public String getBetDisOdds() {
        return betDisOdds;
    }

    /**
     *
     * @param betDisOdds
     * The bet_dis_odds
     */
    public void setBetDisOdds(String betDisOdds) {
        this.betDisOdds = betDisOdds;
    }

    /**
     *
     * @return
     * The leagueName
     */
    public String getLeagueName() {
        return leagueName;
    }

    /**
     *
     * @param leagueName
     * The league_name
     */
    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    /**
     *
     * @return
     * The osBet
     */
    public String getOsBet() {
        return osBet;
    }

    /**
     *
     * @param osBet
     * The os_bet
     */
    public void setOsBet(String osBet) {
        this.osBet = osBet;
    }

    /**
     *
     * @return
     * The betOddVal
     */
    public String getBetOddVal() {
        return betOddVal;
    }

    /**
     *
     * @param betOddVal
     * The bet_odd_val
     */
    public void setBetOddVal(String betOddVal) {
        this.betOddVal = betOddVal;
    }

    /**
     *
     * @return
     * The isPoolBet
     */
    public String getIsPoolBet() {
        return isPoolBet;
    }

    /**
     *
     * @param isPoolBet
     * The is_pool_bet
     */
    public void setIsPoolBet(String isPoolBet) {
        this.isPoolBet = isPoolBet;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The subleagueId
     */
    public String getSubleagueId() {
        return subleagueId;
    }

    /**
     *
     * @param subleagueId
     * The subleague_id
     */
    public void setSubleagueId(String subleagueId) {
        this.subleagueId = subleagueId;
    }

    /**
     *
     * @return
     * The scheduledTime
     */
    public String getScheduledTime() {
        return scheduledTime;
    }

    /**
     *
     * @param scheduledTime
     * The scheduled_time
     */
    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    /**
     *
     * @return
     * The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime
     * The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return
     * The cstScheduledTime
     */
    public String getCstScheduledTime() {
        return cstScheduledTime;
    }

    /**
     *
     * @param cstScheduledTime
     * The cst_scheduled_time
     */
    public void setCstScheduledTime(String cstScheduledTime) {
        this.cstScheduledTime = cstScheduledTime;
    }

    /**
     *
     * @return
     * The cstStartTime
     */
    public String getCstStartTime() {
        return cstStartTime;
    }

    /**
     *
     * @param cstStartTime
     * The cst_start_time
     */
    public void setCstStartTime(String cstStartTime) {
        this.cstStartTime = cstStartTime;
    }

    /**
     *
     * @return
     * The teamANumber
     */
    public String getTeamANumber() {
        return teamANumber;
    }

    /**
     *
     * @param teamANumber
     * The team_A_number
     */
    public void setTeamANumber(String teamANumber) {
        this.teamANumber = teamANumber;
    }

    /**
     *
     * @return
     * The teamAName
     */
    public String getTeamAName() {
        return teamAName;
    }

    /**
     *
     * @param teamAName
     * The team_A_name
     */
    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    /**
     *
     * @return
     * The teamANickname
     */
    public String getTeamANickname() {
        return teamANickname;
    }

    /**
     *
     * @param teamANickname
     * The team_A_nickname
     */
    public void setTeamANickname(String teamANickname) {
        this.teamANickname = teamANickname;
    }

    /**
     *
     * @return
     * The teamAAbbr
     */
    public String getTeamAAbbr() {
        return teamAAbbr;
    }

    /**
     *
     * @param teamAAbbr
     * The team_A_abbr
     */
    public void setTeamAAbbr(String teamAAbbr) {
        this.teamAAbbr = teamAAbbr;
    }

    /**
     *
     * @return
     * The teamAScore
     */
    public String getTeamAScore() {
        return teamAScore;
    }

    /**
     *
     * @param teamAScore
     * The team_A_score
     */
    public void setTeamAScore(String teamAScore) {
        this.teamAScore = teamAScore;
    }

    /**
     *
     * @return
     * The teamAPeriod
     */
    public String getTeamAPeriod() {
        return teamAPeriod;
    }

    /**
     *
     * @param teamAPeriod
     * The team_A_period
     */
    public void setTeamAPeriod(String teamAPeriod) {
        this.teamAPeriod = teamAPeriod;
    }

    /**
     *
     * @return
     * The teamBNumber
     */
    public String getTeamBNumber() {
        return teamBNumber;
    }

    /**
     *
     * @param teamBNumber
     * The team_B_number
     */
    public void setTeamBNumber(String teamBNumber) {
        this.teamBNumber = teamBNumber;
    }

    /**
     *
     * @return
     * The teamBName
     */
    public String getTeamBName() {
        return teamBName;
    }

    /**
     *
     * @param teamBName
     * The team_B_name
     */
    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    /**
     *
     * @return
     * The teamBNickname
     */
    public String getTeamBNickname() {
        return teamBNickname;
    }

    /**
     *
     * @param teamBNickname
     * The team_B_nickname
     */
    public void setTeamBNickname(String teamBNickname) {
        this.teamBNickname = teamBNickname;
    }

    /**
     *
     * @return
     * The teamBAbbr
     */
    public String getTeamBAbbr() {
        return teamBAbbr;
    }

    /**
     *
     * @param teamBAbbr
     * The team_B_abbr
     */
    public void setTeamBAbbr(String teamBAbbr) {
        this.teamBAbbr = teamBAbbr;
    }

    /**
     *
     * @return
     * The teamBScore
     */
    public String getTeamBScore() {
        return teamBScore;
    }

    /**
     *
     * @param teamBScore
     * The team_B_score
     */
    public void setTeamBScore(String teamBScore) {
        this.teamBScore = teamBScore;
    }

    /**
     *
     * @return
     * The teamBPeriod
     */
    public String getTeamBPeriod() {
        return teamBPeriod;
    }

    /**
     *
     * @param teamBPeriod
     * The team_B_period
     */
    public void setTeamBPeriod(String teamBPeriod) {
        this.teamBPeriod = teamBPeriod;
    }

    /**
     *
     * @return
     * The matPeriod
     */
    public String getMatPeriod() {
        return matPeriod;
    }

    /**
     *
     * @param matPeriod
     * The mat_period
     */
    public void setMatPeriod(String matPeriod) {
        this.matPeriod = matPeriod;
    }

    /**
     *
     * @return
     * The matTimer
     */
    public String getMatTimer() {
        return matTimer;
    }

    /**
     *
     * @param matTimer
     * The mat_timer
     */
    public void setMatTimer(String matTimer) {
        this.matTimer = matTimer;
    }

    /**
     *
     * @return
     * The matStatus
     */
    public String getMatStatus() {
        return matStatus;
    }

    /**
     *
     * @param matStatus
     * The mat_status
     */
    public void setMatStatus(String matStatus) {
        this.matStatus = matStatus;
    }

    /**
     *
     * @return
     * The refId
     */
    public String getRefId() {
        return refId;
    }

    /**
     *
     * @param refId
     * The ref_id
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }

    /**
     *
     * @return
     * The matProcessed
     */
    public String getMatProcessed() {
        return matProcessed;
    }

    /**
     *
     * @param matProcessed
     * The mat_processed
     */
    public void setMatProcessed(String matProcessed) {
        this.matProcessed = matProcessed;
    }

    /**
     *
     * @return
     * The matCreated
     */
    public String getMatCreated() {
        return matCreated;
    }

    /**
     *
     * @param matCreated
     * The mat_created
     */
    public void setMatCreated(String matCreated) {
        this.matCreated = matCreated;
    }

    /**
     *
     * @return
     * The Odd
     */
    public ArrayList<Odd> getOdds() {
        return odds;
    }

    /**
     *
     * @param Odd
     * The Odd
     */
    public void setOdds(ArrayList<Odd> Odd) {
        this.odds = Odd;
    }

    /**
     *
     * @return
     * The User
     */
    public User getUser() {
        return User;
    }

    /**
     *
     * @param User
     * The User
     */
    public void setUser(User User) {
        this.User = User;
    }

    /**
     *
     * @return
     * The result
     */
    public String getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     *
     * @return
     * The teamALogo
     */
    public String getTeamALogo() {
        return teamALogo;
    }

    /**
     *
     * @param teamALogo
     * The team_A_logo
     */
    public void setTeamALogo(String teamALogo) {
        this.teamALogo = teamALogo;
    }

    /**
     *
     * @return
     * The teamBLogo
     */
    public String getTeamBLogo() {
        return teamBLogo;
    }

    /**
     *
     * @param teamBLogo
     * The team_B_logo
     */
    public void setTeamBLogo(String teamBLogo) {
        this.teamBLogo = teamBLogo;
    }

}
